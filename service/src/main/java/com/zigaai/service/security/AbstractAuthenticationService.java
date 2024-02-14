// package com.zigaai.service.security;
//
// import com.zigaai.constants.RedisConstant;
// import com.zigaai.infra.service.LocalAuthenticationService;
// import com.zigaai.mapper.AuthenticationMapper;
// import com.zigaai.model.security.AuthenticationModel;
// import com.zigaai.security.model.SystemUser;
// import com.zigaai.security.service.TokenCacheService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.stereotype.Service;
//
// import java.util.Arrays;
// import java.util.UUID;
//
// @Service
// @RequiredArgsConstructor
// public abstract class AbstractAuthenticationService<T extends AuthenticationModel> implements LocalAuthenticationService {
//
//     private final AuthenticationMapper<T> authenticationMapper;
//
//     private final TokenCacheService tokenCacheService;
//
//     @Override
//     public int logout(SystemUser systemUser) {
//         tokenCacheService.clearRefreshToken(getKey(), systemUser.getUsername());
//         return this.refreshSalt(systemUser.getUsername());
//     }
//
//     @Override
//     public int refreshSalt(String username) {
//         String salt = UUID.randomUUID().toString();
//         int count = authenticationMapper.updateSalt(username, salt);
//         RedisTemplate<String, Object> redisTemplate = tokenCacheService.getRedisTemplate();
//         redisTemplate.delete(Arrays.asList(
//                 RedisConstant.SYS_USER_INFO(getKey(), username),
//                 RedisConstant.USER_SALT(getKey(), username)
//         ));
//         return count;
//     }
//
//     // @Override
//     // public String getSaltByUsername(String username) {
//     //     String key = RedisConstant.USER_SALT(getKey(), username);
//     //     RedisTemplate<String, Object> redisTemplate = tokenCacheService.getRedisTemplate();
//     //     String salt = (String) redisTemplate.opsForValue().get(key);
//     //     if (StringUtils.isNoneBlank(salt)) {
//     //         return salt;
//     //     }
//     //     salt = authenticationMapper.getSaltByUsername(username);
//     //     redisTemplate.opsForValue().set(key, salt, 3, TimeUnit.DAYS);
//     //     return salt;
//     // }
//
//     // @Override
//     // public String getSaltById(Long id) {
//     //     return authenticationMapper.getSaltById(id);
//     // }
//
// }
