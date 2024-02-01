package com.foo.service;

import com.foo.constants.RedisConstant;
import com.foo.infra.security.AuthenticationModel;
import com.foo.infra.security.MultiAuthenticationUserDetailsService;
import com.foo.infra.security.SystemUser;
import com.foo.mapper.AuthenticationMapper;
import com.foo.mapper.PagePermissionMapper;
import com.foo.mapper.RoleMapper;
import com.foo.model.entity.PagePermission;
import com.foo.model.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public abstract class AbstractMultiAuthenticationUserDetailsService<T extends AuthenticationModel> implements MultiAuthenticationUserDetailsService {

    private final AuthenticationMapper<T> authenticationMapper;

    private final RoleMapper roleMapper;

    private final PagePermissionMapper pagePermissionMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String key = RedisConstant.SYS_USER_INFO.apply(getKey().name(), username);
        SystemUser systemUser = (SystemUser) redisTemplate.opsForValue().get(key);
        if (systemUser != null) {
            return systemUser;
        }
        T sysUser = authenticationMapper.getByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("系统用户不存在");
        }
        List<Role> roleList = roleMapper.listBySysUserId(sysUser.getId(), getKey());
        List<PagePermission> pagePermissionList = Collections.emptyList();
        if (!CollectionUtils.isEmpty(roleList)) {
            List<Long> roleIdList = roleList.stream().map(Role::getId).toList();
            pagePermissionList = pagePermissionMapper.listByRoleIds(roleIdList);
        }
        systemUser = this.buildSystemUser(sysUser, roleList, pagePermissionList);
        redisTemplate.opsForValue().set(key, systemUser, 3, TimeUnit.DAYS);
        return systemUser;
    }

    protected abstract SystemUser buildSystemUser(T sysUser, List<Role> roleList, List<PagePermission> pagePermissionList);

}
