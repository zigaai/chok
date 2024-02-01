package com.foo.service;

import com.foo.enumeration.SysUserType;
import com.foo.infra.security.SystemUser;
import com.foo.mapper.AdminMapper;
import com.foo.mapper.PagePermissionMapper;
import com.foo.mapper.RoleMapper;
import com.foo.model.entity.Admin;
import com.foo.model.entity.PagePermission;
import com.foo.model.entity.Role;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminUserDetailsService extends AbstractMultiAuthenticationUserDetailsService<Admin> {

    public AdminUserDetailsService(AdminMapper adminMapper,
                                   RoleMapper roleMapper,
                                   PagePermissionMapper pagePermissionMapper,
                                   RedisTemplate<String, Object> redisTemplate) {
        super(adminMapper, roleMapper, pagePermissionMapper, redisTemplate);
    }

    @Override
    public SysUserType getKey() {
        return SysUserType.ADMIN;
    }

    @Override
    protected SystemUser buildSystemUser(Admin admin, List<Role> roleList, List<PagePermission> pagePermissionList) {
        return SystemUser.of(admin, roleList, pagePermissionList);
    }

}
