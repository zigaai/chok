// package com.zigaai.service.security;
//
// import com.zigaai.mapper.UserMapper;
// import com.zigaai.model.entity.User;
// import com.zigaai.security.service.TokenCacheService;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.stereotype.Component;
//
// @Component
// public class NormalUserAuthenticationService extends AbstractAuthenticationService<User> {
//
//     public NormalUserAuthenticationService(UserMapper userMapper,
//                                            TokenCacheService tokenCacheService) {
//         super(userMapper, tokenCacheService);
//     }
//
//     @Override
//     public String getKey() {
//         return "user";
//     }
//
// }
