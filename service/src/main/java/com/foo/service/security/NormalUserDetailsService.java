package com.foo.service.security;

import com.foo.mapper.MenuMapper;
import com.foo.mapper.RoleMapper;
import com.foo.mapper.UserMapper;
import com.foo.model.entity.Menu;
import com.foo.model.entity.Role;
import com.foo.model.entity.User;
import com.zigaai.model.SystemUser;
import com.zigaai.properties.CustomSecurityProperties;
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
