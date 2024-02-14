// package com.zigaai.service.security;
//
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.nimbusds.jose.JOSEException;
// import com.nimbusds.jose.JWSObject;
// import com.zigaai.constants.RedisConstant;
// import com.zigaai.infra.service.LocalAuthenticationService;
// import com.zigaai.model.security.PayloadDTO;
// import com.zigaai.security.converter.SystemUserConvertor;
// import com.zigaai.security.model.SystemUser;
// import com.zigaai.security.model.SystemUserVO;
// import com.zigaai.security.properties.CustomSecurityProperties;
// import com.zigaai.security.service.TokenCacheService;
// import com.zigaai.security.utils.JWTUtil;
// import com.zigaai.security.utils.SecurityUtil;
// import com.zigaai.strategy.StrategyFactory;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.apache.commons.lang3.StringUtils;
// import org.apache.commons.lang3.tuple.Pair;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.stereotype.Service;
//
// import java.security.NoSuchAlgorithmException;
// import java.security.spec.InvalidKeySpecException;
// import java.text.ParseException;
//
// @Slf4j
// @Service
// @RequiredArgsConstructor
// public class AuthenticationHandler {
//
//     private final StrategyFactory<String, LocalAuthenticationService> authenticationServiceStrategy;
//
//     private final RedisTemplate<String, Object> redisTemplate;
//
//     private final TokenCacheService tokenCacheService;
//
//     private final CustomSecurityProperties securityProperties;
//
//     public int logout(String token) {
//         if (StringUtils.isBlank(token)) {
//             return 0;
//         }
//         try {
//             Pair<JWSObject, PayloadDTO> pair = JWTUtil.parseUnverified(token);
//             PayloadDTO payload = pair.getRight();
//             SystemUser systemUser = (SystemUser) redisTemplate.opsForValue().get(RedisConstant.SYS_USER_INFO(payload.getUserType(), payload.getUsername()));
//             if (systemUser == null || StringUtils.isBlank(systemUser.getSalt())) {
//                 return 0;
//             }
//             JWTUtil.check(pair.getLeft(), payload, securityProperties.getKeyPairs());
//             LocalAuthenticationService authenticationService = authenticationServiceStrategy.getStrategy(payload.getUserType());
//             return authenticationService.logout(systemUser);
//         } catch (JOSEException | ParseException | JsonProcessingException | NoSuchAlgorithmException |
//                  InvalidKeySpecException e) {
//             if (log.isDebugEnabled()) {
//                 log.debug("登出异常: ", e);
//             }
//         }
//         return 0;
//     }
//
//     public SystemUserVO getInfo() {
//         SystemUser systemUser = SecurityUtil.currentUser();
//         return SystemUserConvertor.INSTANCE.toVO(systemUser);
//     }
//
//     // public UPMSToken refreshToken(String refreshToken) throws JsonProcessingException, JOSEException, NoSuchAlgorithmException, InvalidKeySpecException {
//     //     String refreshTokenKey = SecurityConstant.CacheKey.REFRESH_TOKEN_INFO(refreshToken);
//     //     PayloadDTO payload = tokenCacheService.getRefreshTokenInfo(refreshToken);
//     //     HashSet<String> userRefreshTokens = tokenCacheService.getRefreshTokens(payload.getUserType(), payload.getUsername());
//     //     if (CollectionUtils.isEmpty(userRefreshTokens) || !userRefreshTokens.contains(refreshToken)) {
//     //         redisTemplate.delete(refreshTokenKey);
//     //         throw new RefreshTokenExpiredException("非法的 refresh token, 请重新登录");
//     //     }
//     //     String salt = authenticationServiceStrategy.getStrategy(payload.getUserType()).getSaltByUsername(payload.getUsername());
//     //     UPMSToken upmsToken = JWTUtil.generateToken(payload, securityProperties.getKeyPairs());
//     //     tokenCacheService.cacheRefreshToken(upmsToken, payload);
//     //     return upmsToken;
//     // }
//
// }
