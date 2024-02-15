package com.zigaai.infra.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.JWSObject;
import com.zigaai.constants.RedisConstant;
import com.zigaai.constants.SecurityConstant;
import com.zigaai.model.security.PayloadDTO;
import com.zigaai.oauth2.constants.OAuth2RedisKeys;
import com.zigaai.security.model.SystemUser;
import com.zigaai.security.service.AuthenticationService;
import com.zigaai.security.service.BaseAuthenticationGrpcServerService;
import com.zigaai.security.service.MultiAuthenticationUserDetailsService;
import com.zigaai.security.utils.JWTUtil;
import com.zigaai.strategy.StrategyFactory;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class AuthenticationServiceImpl extends BaseAuthenticationGrpcServerService implements AuthenticationService {

    private final StrategyFactory<String, MultiAuthenticationUserDetailsService> userDetailsServiceStrategy;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public String getSalt(String userType, String username) {
        return userDetailsServiceStrategy.getStrategy(userType).getSalt(username);
    }

    @Override
    public SystemUser loadUserByUsername(String userType, String username) {
        return (SystemUser) userDetailsServiceStrategy.getStrategy(userType).loadUserByUsername(username);
    }

    @SuppressWarnings("unchecked")
    public void logout(String token) {
        Pair<JWSObject, PayloadDTO> pair;
        try {
            pair = JWTUtil.parseUnverified(token);
        } catch (ParseException | JsonProcessingException e) {
            return;
        }
        PayloadDTO payload = pair.getRight();
        String userType = payload.getUserType();
        String username = payload.getUsername();
        String refreshTokenKey = SecurityConstant.CacheKey.USER_REFRESH_TOKEN(userType, username);
        List<String> cacheKeyList = new ArrayList<>();
        cacheKeyList.add(refreshTokenKey);
        HashSet<String> refreshTokens = (HashSet<String>) redisTemplate.opsForValue().get(refreshTokenKey);
        if (!CollectionUtils.isEmpty(refreshTokens)) {
            for (String item : refreshTokens) {
                cacheKeyList.add(SecurityConstant.CacheKey.REFRESH_TOKEN_INFO(item));
            }
        }
        cacheKeyList.add(SecurityConstant.CacheKey.USER_SALT(userType, username));
        cacheKeyList.add(RedisConstant.SYS_USER_INFO(userType, username));
        String authorizationIdKey = OAuth2RedisKeys.USER_OAUTH2_AUTHORIZATION_ID(userType, username);
        cacheKeyList.add(authorizationIdKey);
        HashSet<String> authorizationIds = (HashSet<String>) redisTemplate.opsForValue().get(authorizationIdKey);
        if (!CollectionUtils.isEmpty(authorizationIds)) {
            for (String id : authorizationIds) {
                cacheKeyList.add(OAuth2RedisKeys.RESOURCE_PRINCIPAL(userType, username, id));
                cacheKeyList.add(OAuth2RedisKeys.OAUTH2_AUTHORIZATION(id));
            }
        }
        String oauth2AccessTokenKey = OAuth2RedisKeys.USER_OAUTH2_ACCESS_TOKEN(userType, username);
        cacheKeyList.add(oauth2AccessTokenKey);
        HashSet<String> userAccessTokens = (HashSet<String>) redisTemplate.opsForValue().get(oauth2AccessTokenKey);
        if (!CollectionUtils.isEmpty(userAccessTokens)) {
            for (String item : userAccessTokens) {
                cacheKeyList.add(OAuth2RedisKeys.ACCESS_TOKEN(item));
            }
        }
        redisTemplate.delete(cacheKeyList);
        MultiAuthenticationUserDetailsService service = userDetailsServiceStrategy.getStrategy(userType);
        if (service != null) {
            service.updateSalt(username);
        }
    }
}
