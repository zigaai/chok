package com.foo.service;

import com.foo.mapper.MenuMapper;
import com.foo.mapper.RoleMapper;
import com.foo.model.convertor.MenuConvertor;
import com.foo.model.dto.command.MenuDTO;
import com.foo.model.entity.Menu;
import com.foo.model.entity.Role;
import com.zigaai.enumeration.TbState;
import com.zigaai.model.SystemUser;
import com.zigaai.properties.CustomSecurityProperties;
import com.zigaai.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 页面权限表 服务实现类
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl {

    private final MenuMapper menuMapper;

    private final RoleMapper roleMapper;

    private final CustomSecurityProperties customSecurityProperties;

    public List<Menu> listCurrentUser() {
        SystemUser user = SecurityUtil.currentUser();
        List<Role> roleList = roleMapper.listBySysUserId(user.getId(), customSecurityProperties.getUserType(user.getUserType()));
        if (CollectionUtils.isEmpty(roleList)) {
            return Collections.emptyList();
        }
        List<Long> roleIds = roleList.stream().map(Role::getId).toList();
        if (roleIds.contains(1L)) {
            return menuMapper.listAll();
        }
        return menuMapper.listByRoleIds(roleIds);
    }

    public int save(MenuDTO data) {
        Menu entity = MenuConvertor.INSTANCE.toEntity(data);
        entity.setIsDeleted(TbState.NORMAL.booleanVal());
        return menuMapper.insert(entity);
    }

    public int update(MenuDTO data) {
        Menu entity = MenuConvertor.INSTANCE.toEntity(data);
        return menuMapper.updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteByIds(List<Long> ids) {
        int count = menuMapper.deleteBatchByIds(ids);
        // AuthenticationService
        return count;
    }
}
