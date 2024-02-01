package com.foo.service.security;

import com.foo.mapper.AdminMapper;
import com.foo.model.entity.Admin;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdminAuthenticationService extends AbstractAuthenticationService<Admin> {

    public AdminAuthenticationService(AdminMapper adminMapper,
                                      RedisTemplate<String, Object> redisTemplate) {
        super(adminMapper, redisTemplate);
    }

    @Override
    public String getKey() {
        return "admin";
    }


}
