package com.zigaai.controller;

import com.zigaai.model.dto.command.MenuDTO;
import com.zigaai.model.common.ResponseData;
import com.zigaai.model.entity.Menu;
import com.zigaai.validation.AddGroup;
import com.zigaai.validation.UpdateGroup;
import com.zigaai.service.MenuServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单页面接口
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

    private final MenuServiceImpl menuService;

    /**
     * 获取当前用户权限
     */
    @GetMapping("/list")
    public ResponseData<List<Menu>> list() {
        List<Menu> list = menuService.listCurrentUser();
        return ResponseData.success(list);
    }

    /**
     * 保存菜单
     */
    @PostMapping
    public ResponseData<Integer> saveMenu(@RequestBody @Validated(AddGroup.class) MenuDTO data) {
        int count = menuService.save(data);
        return ResponseData.success(count);
    }

    /**
     * 编辑菜单
     */
    @PutMapping
    public ResponseData<Integer> updateMenu(@RequestBody @Validated(UpdateGroup.class) MenuDTO data) {
        int count = menuService.update(data);
        return ResponseData.success(count);
    }

    /**
     * 批量删除菜单
     *
     * @param ids id列表
     */
    @DeleteMapping("/batch")
    public ResponseData<Integer> deleteMenuBatch(@RequestBody List<Long> ids) {
        int count = menuService.deleteByIds(ids);
        return ResponseData.success(count);
    }
}
