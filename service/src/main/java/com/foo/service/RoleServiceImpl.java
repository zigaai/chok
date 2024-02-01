package com.foo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foo.mapper.RoleMapper;
import com.foo.model.convertor.RoleConvertor;
import com.foo.model.dto.command.RoleDTO;
import com.foo.model.dto.query.RoleQuery;
import com.foo.model.entity.Role;
import com.foo.model.vo.RoleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl {

    private final RoleMapper roleMapper;

    public IPage<RoleVO> page(RoleQuery params) {
        Page<RoleVO> page = new Page<>();
        List<RoleVO> records = roleMapper.listByCondition(page, params);
        page.setRecords(records);
        return page;
    }

    public int add(RoleDTO data) {
        Role entity = RoleConvertor.INSTANCE.toEntity(data);
        return roleMapper.insert(entity);
    }

    public int update(RoleDTO data) {
        Role entity = RoleConvertor.INSTANCE.toEntity(data);
        return roleMapper.updateById(entity);
    }

    public int deleteById(Long id) {
        return roleMapper.deleteById(id);
    }

}
