package com.foo.service.security;

import com.foo.mapper.AdminMapper;
import com.foo.mapper.MenuMapper;
import com.foo.mapper.RoleMapper;
import com.foo.model.entity.Admin;
import com.foo.model.entity.Menu;
import com.foo.model.entity.Role;
import com.zigaai.model.SystemUser;
import com.zigaai.properties.CustomSecurityProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminUserDetailsService extends AbstractMultiAuthenticationUserDetailsService<Admin> {

    public AdminUserDetailsService(AdminMapper adminMapper,
                                   RoleMapper roleMapper,
                                   MenuMapper menuMapper,
                                   RedisTemplate<String, Object> redisTemplate,
                                   CustomSecurityProperties securityProperties) {
        super(adminMapper, roleMapper, menuMapper, redisTemplate, securityProperties);
    }

    @Override
    public String getKey() {
        return "admin";
    }

    @Override
    protected SystemUser buildSystemUser(Admin admin, List<Role> roleList, List<Menu> menuList) {
        return SystemUser.of(admin, roleList, menuList);
    }

}
