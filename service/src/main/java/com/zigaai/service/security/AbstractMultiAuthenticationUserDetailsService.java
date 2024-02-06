package com.zigaai.service.security;

import com.zigaai.constants.RedisConstant;
import com.zigaai.mapper.AuthenticationMapper;
import com.zigaai.mapper.MenuMapper;
import com.zigaai.mapper.RoleMapper;
import com.zigaai.model.entity.Menu;
import com.zigaai.model.entity.Role;
import com.zigaai.model.security.AuthenticationModel;
import com.zigaai.security.model.SystemUser;
import com.zigaai.security.properties.CustomSecurityProperties;
import com.zigaai.security.service.MultiAuthenticationUserDetailsService;
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

    private final MenuMapper menuMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    private final CustomSecurityProperties securityProperties;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomSecurityProperties.Context userType = securityProperties.getUserType(getKey());
        String key = RedisConstant.SYS_USER_INFO(getKey(), username);
        SystemUser systemUser = (SystemUser) redisTemplate.opsForValue().get(key);
        if (systemUser != null) {
            return systemUser;
        }
        T sysUser = authenticationMapper.getByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("系统用户不存在");
        }
        List<Role> roleList = roleMapper.listBySysUserId(sysUser.getId(), userType);
        List<Menu> menuList = Collections.emptyList();
        if (!CollectionUtils.isEmpty(roleList)) {
            List<Long> roleIdList = roleList.stream().map(Role::getId).toList();
            menuList = menuMapper.listByRoleIds(roleIdList);
        }
        systemUser = this.buildSystemUser(sysUser, roleList, menuList);
        redisTemplate.opsForValue().set(key, systemUser, securityProperties.getToken().getRefreshTimeToLive(), TimeUnit.SECONDS);
        return systemUser;
    }

    protected abstract SystemUser buildSystemUser(T sysUser, List<Role> roleList, List<Menu> menuList);

}