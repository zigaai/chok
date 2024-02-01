package com.foo.service.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.foo.constants.RedisConstant;
import com.foo.infra.security.AuthenticationService;
import com.foo.infra.service.RedisService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.zigaai.converter.SystemUserConvertor;
import com.zigaai.exception.RefreshTokenExpiredException;
import com.zigaai.model.PayloadDTO;
import com.zigaai.model.SystemUser;
import com.zigaai.model.SystemUserVO;
import com.zigaai.model.UPMSToken;
import com.zigaai.strategy.StrategyFactory;
import com.zigaai.utils.JWTUtil;
import com.zigaai.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationHandler {

    private final StrategyFactory<String, AuthenticationService> authenticationServiceStrategy;

    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisService redisService;

    public int logout(String token) {
        if (StringUtils.isBlank(token)) {
            return 0;
        }
        try {
            Pair<JWSObject, PayloadDTO> pair = JWTUtil.parseUnverified(token);
            PayloadDTO payload = pair.getRight();
            SystemUser systemUser = (SystemUser) redisTemplate.opsForValue().get(RedisConstant.SYS_USER_INFO(payload.getUserType(), payload.getUsername()));
            if (systemUser == null || StringUtils.isBlank(systemUser.getSalt())) {
                return 0;
            }
            JWTUtil.check(pair.getLeft(), payload, systemUser.getSalt());
            AuthenticationService authenticationService = authenticationServiceStrategy.getStrategy(payload.getUserType());
            return authenticationService.logout(systemUser);
        } catch (JOSEException | ParseException | JsonProcessingException e) {
            if (log.isDebugEnabled()) {
                log.debug("登出异常: ", e);
            }
        }
        return 0;
    }

    public SystemUserVO getInfo() {
        SystemUser systemUser = SecurityUtil.currentUser();
        return SystemUserConvertor.INSTANCE.toVO(systemUser);
    }

    @SuppressWarnings("unchecked")
    public UPMSToken refreshToken(String refreshToken) throws JsonProcessingException, JOSEException {
        String refreshTokenKey = RedisConstant.REFRESH_TOKEN(refreshToken);
        PayloadDTO payload = (PayloadDTO) redisTemplate.opsForValue().get(refreshTokenKey);
        if (payload == null) {
            throw new RefreshTokenExpiredException("refresh token 已过期, 请重新登录");
        }
        String userRefreshTokenKey = RedisConstant.USER_REFRESH_TOKEN(payload.getUserType(), payload.getUsername());
        HashSet<String> userRefreshTokens = (HashSet<String>) redisTemplate.opsForValue().get(userRefreshTokenKey);
        if (CollectionUtils.isEmpty(userRefreshTokens) || !userRefreshTokens.contains(refreshToken)) {
            redisTemplate.delete(refreshTokenKey);
            throw new RefreshTokenExpiredException("非法的 refresh token, 请重新登录");
        }
        String salt = authenticationServiceStrategy.getStrategy(payload.getUserType()).getSaltByUsername(payload.getUsername());
        UPMSToken upmsToken = JWTUtil.generateToken(payload, salt);
        redisService.cacheRefreshToken(upmsToken, payload);
        return upmsToken;
    }

}
