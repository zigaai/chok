package com.foo.service;

import com.foo.enumeration.SysUserType;
import com.foo.infra.security.SystemUser;
import com.foo.mapper.PagePermissionMapper;
import com.foo.mapper.RoleMapper;
import com.foo.mapper.UserMapper;
import com.foo.model.entity.PagePermission;
import com.foo.model.entity.Role;
import com.foo.model.entity.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NormalUserDetailsService extends AbstractMultiAuthenticationUserDetailsService<User> {


    public NormalUserDetailsService(UserMapper userMapper,
                                    RoleMapper roleMapper,
                                    PagePermissionMapper pagePermissionMapper,
                                    RedisTemplate<String, Object> redisTemplate) {
        super(userMapper, roleMapper, pagePermissionMapper, redisTemplate);
    }

    @Override
    public SysUserType getKey() {
        return SysUserType.USER;
    }

    @Override
    protected SystemUser buildSystemUser(User user, List<Role> roleList, List<PagePermission> pagePermissionList) {
        return SystemUser.of(user, roleList, pagePermissionList);
    }
}
