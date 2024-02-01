package com.foo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foo.mapper.MenuMapper;
import com.foo.model.convertor.MenuConvertor;
import com.foo.model.dto.command.MenuDTO;
import com.foo.model.dto.query.MenuQuery;
import com.foo.model.entity.Menu;
import com.foo.model.vo.MenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private final MenuMapper MenuMapper;

    public IPage<MenuVO> page(MenuQuery params) {
        Page<MenuVO> page = new Page<>();
        List<MenuVO> records = MenuMapper.listByCondition(page, params);
        page.setRecords(records);
        return page;
    }

    public int add(MenuDTO data) {
        Menu entity = MenuConvertor.INSTANCE.toEntity(data);
        return MenuMapper.insert(entity);
    }

    public int update(MenuDTO data) {
        Menu entity = MenuConvertor.INSTANCE.toEntity(data);
        return MenuMapper.updateById(entity);
    }

    public int deleteById(Long id) {
        return MenuMapper.deleteById(id);
    }

}
