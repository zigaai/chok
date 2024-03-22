package com.zigaai.infra.security;

import com.zigaai.mapper.MenuMapper;
import com.zigaai.mapper.RoleMapper;
import com.zigaai.mapper.UserMapper;
import com.zigaai.model.entity.Menu;
import com.zigaai.model.entity.Role;
import com.zigaai.model.entity.User;
import com.zigaai.security.model.SystemUser;
import com.zigaai.security.properties.CustomSecurityProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NormalUserDetailsService extends AbstractMultiAuthenticationUserDetailsService<User> {


    public NormalUserDetailsService(UserMapper userMapper,
                                    RoleMapper roleMapper,
                                    MenuMapper menuMapper,
                                    RedisTemplate<String, Object> redisTemplate,
                                    CustomSecurityProperties securityProperties) {
        super(userMapper, roleMapper, menuMapper, redisTemplate, securityProperties);
    }

    @Override
    public String getKey() {
        return "user";
    }

    @Override
    protected SystemUser buildSystemUser(User user, List<Role> roleList, List<Menu> menuList) {
        return SystemUser.of(user, roleList, menuList);
    }
}
