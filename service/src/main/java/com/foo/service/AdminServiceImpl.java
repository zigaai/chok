package com.foo.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import com.foo.model.entity.Admin;
import com.foo.model.dto.command.AdminDTO;
import com.foo.model.vo.AdminVO;
import com.foo.model.dto.query.AdminQuery;
import com.foo.model.convertor.AdminConvertor;
import com.foo.mapper.AdminMapper;
import com.foo.service.AdminService;

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
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    @Override
    public IPage<AdminVO> page(AdminQuery params) {
        Page<AdminVO> page = new Page<>();
        List<AdminVO> records = adminMapper.listByCondition(page, params);
        page.setRecords(records);
        return page;
    }

    @Override
    public int add(AdminDTO data) {
        Admin entity = AdminConvertor.INSTANCE.toEntity(data);
        return adminMapper.insert(entity);
    }

    @Override
    public int update(AdminDTO data) {
        Admin entity = AdminConvertor.INSTANCE.toEntity(data);
        return adminMapper.updateById(entity);
    }

    @Override
    public int deleteById(Long id) {
        return adminMapper.deleteById(id);
    }

}
