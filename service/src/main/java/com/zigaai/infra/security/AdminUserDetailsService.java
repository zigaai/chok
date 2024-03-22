package com.zigaai.infra.security;

import com.zigaai.mapper.AdminMapper;
import com.zigaai.mapper.MenuMapper;
import com.zigaai.mapper.RoleMapper;
import com.zigaai.model.entity.Admin;
import com.zigaai.model.entity.Menu;
import com.zigaai.model.entity.Role;
import com.zigaai.security.model.SystemUser;
import com.zigaai.security.properties.CustomSecurityProperties;
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
