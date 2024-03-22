package com.zigaai.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zigaai.model.common.ResponseData;
import com.zigaai.model.dto.command.Oauth2RegisteredClientDTO;
import com.zigaai.model.dto.query.Oauth2RegisteredClientQuery;
import com.zigaai.model.entity.Oauth2RegisteredClient;
import com.zigaai.service.Oauth2RegisteredClientServiceImpl;
import com.zigaai.validation.AddGroup;
import com.zigaai.validation.DeleteGroup;
import com.zigaai.validation.QueryGroup;
import com.zigaai.validation.UpdateGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * OAuth2 客户端管理
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/registered-client")
public class Oauth2RegisteredClientController {

    private final Oauth2RegisteredClientServiceImpl service;

    @GetMapping("/page")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseData<Page<Oauth2RegisteredClient>> page(@Validated(QueryGroup.class) Oauth2RegisteredClientQuery params) {
        Page<Oauth2RegisteredClient> page = service.page(params);
        return ResponseData.success(page);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseData<Oauth2RegisteredClientDTO> save(@RequestBody @Validated(AddGroup.class) Oauth2RegisteredClientDTO data) {
        service.save(data);
        return ResponseData.success("保存成功", data);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseData<Integer> update(@RequestBody @Validated(UpdateGroup.class) Oauth2RegisteredClientDTO data) throws JsonProcessingException {
        int count = service.update(data);
        return ResponseData.success("编辑成功", count);
    }

    @PutMapping("/state/batch")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseData<Integer> updateStateBatch(@RequestBody @Validated(DeleteGroup.class) Oauth2RegisteredClientDTO data) {
        int count = service.updateStateBatch(data);
        return ResponseData.success("操作成功", count);
    }


}
