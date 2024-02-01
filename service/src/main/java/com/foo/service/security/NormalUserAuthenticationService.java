package com.foo.service.security;

import com.foo.mapper.UserMapper;
import com.foo.model.entity.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class NormalUserAuthenticationService extends AbstractAuthenticationService<User> {

    public NormalUserAuthenticationService(UserMapper userMapper,
                                           RedisTemplate<String, Object> redisTemplate) {
        super(userMapper, redisTemplate);
    }

    @Override
    public String getKey() {
        return "user";
    }

}
