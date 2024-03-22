package com.zigaai.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zigaai.mapper.AdminMapper;
import com.zigaai.model.convertor.AdminConvertor;
import com.zigaai.model.dto.command.AdminDTO;
import com.zigaai.model.dto.query.AdminQuery;
import com.zigaai.model.entity.Admin;
import com.zigaai.model.vo.AdminVO;
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
        Page<AdminVO> page = new Page<>(params.getCurrent(), params.getSize());
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
