package com.foo.service.security;

import com.foo.constants.RedisConstant;
import com.foo.infra.security.AuthenticationService;
import com.foo.mapper.AuthenticationMapper;
import com.zigaai.model.AuthenticationModel;
import com.zigaai.model.SystemUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public abstract class AbstractAuthenticationService<T extends AuthenticationModel> implements AuthenticationService {

    private final AuthenticationMapper<T> authenticationMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    @SuppressWarnings("unchecked")
    public int logout(SystemUser systemUser) {
        String username = systemUser.getUsername();
        HashSet<String> existRefreshTokens = (HashSet<String>) redisTemplate.opsForValue().get(RedisConstant.USER_REFRESH_TOKEN(getKey(), username));
        List<String> keys = new ArrayList<>();
        if (!CollectionUtils.isEmpty(existRefreshTokens)) {
            for (String item : existRefreshTokens) {
                keys.add(RedisConstant.REFRESH_TOKEN(item));
            }
        }
        keys.add(RedisConstant.USER_REFRESH_TOKEN(getKey(), username));
        redisTemplate.delete(keys);
        return this.refreshSalt(systemUser.getUsername());
    }

    @Override
    public int refreshSalt(String username) {
        String salt = UUID.randomUUID().toString();
        int count = authenticationMapper.updateSalt(username, salt);
        redisTemplate.delete(Arrays.asList(
                RedisConstant.SYS_USER_INFO(getKey(), username),
                RedisConstant.USER_SALT(getKey(), username)
        ));
        return count;
    }

    @Override
    public String getSaltByUsername(String username) {
        String key = RedisConstant.USER_SALT(getKey(), username);
        String salt = (String) redisTemplate.opsForValue().get(key);
        if (StringUtils.isNoneBlank(salt)) {
            return salt;
        }
        salt = authenticationMapper.getSaltByUsername(username);
        redisTemplate.opsForValue().set(key, salt, 3, TimeUnit.DAYS);
        return salt;
    }

    // @Override
    // public String getSaltById(Long id) {
    //     return authenticationMapper.getSaltById(id);
    // }

}
