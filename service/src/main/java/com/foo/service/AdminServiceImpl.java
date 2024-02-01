package com.foo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foo.mapper.AdminMapper;
import com.foo.model.convertor.AdminConvertor;
import com.foo.model.dto.command.AdminDTO;
import com.foo.model.dto.query.AdminQuery;
import com.foo.model.entity.Admin;
import com.foo.model.vo.AdminVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl {

    private final AdminMapper adminMapper;

    public IPage<AdminVO> page(AdminQuery params) {
        Page<AdminVO> page = new Page<>();
        List<AdminVO> records = adminMapper.listByCondition(page, params);
        page.setRecords(records);
        return page;
    }

    public List<AdminVO> list(AdminQuery params) {
        return adminMapper.listByCondition(null, params);
    }

    public int add(AdminDTO data) {
        Admin entity = AdminConvertor.INSTANCE.toEntity(data);
        return adminMapper.insert(entity);
    }

    public int update(AdminDTO data) {
        Admin entity = AdminConvertor.INSTANCE.toEntity(data);
        return adminMapper.updateById(entity);
    }

    public int deleteById(Long id) {
        return adminMapper.deleteById(id);
    }

}
