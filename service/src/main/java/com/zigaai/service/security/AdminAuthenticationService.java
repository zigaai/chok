package com.zigaai.service.security;

import com.zigaai.mapper.AdminMapper;
import com.zigaai.mapper.AuthenticationMapper;
import com.zigaai.model.entity.Admin;
import com.zigaai.security.service.TokenCacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdminAuthenticationService extends AbstractAuthenticationService<Admin> {

    public AdminAuthenticationService(AuthenticationMapper<Admin> authenticationMapper,
                                      TokenCacheService tokenCacheService) {
        super(authenticationMapper, tokenCacheService);
    }

    @Override
    public String getKey() {
        return "admin";
    }


}
