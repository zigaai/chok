package com.zigaai.controller;

import com.zigaai.infra.security.AuthenticationServiceImpl;
import com.zigaai.model.common.ResponseData;
import com.zigaai.security.converter.SystemUserConvertor;
import com.zigaai.security.model.SystemUser;
import com.zigaai.security.model.SystemUserVO;
import com.zigaai.security.utils.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限相关接口
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class AuthenticationController {

    private final BearerTokenResolver bearerTokenResolver;

    private final AuthenticationServiceImpl authenticationService;

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public ResponseData<Object> getInfo() {
        SystemUser systemUser = SecurityUtil.currentUser();
        SystemUserVO info = SystemUserConvertor.INSTANCE.toVO(systemUser);
        return ResponseData.success(info);
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public ResponseData<Void> logout(HttpServletRequest request) {
        String token = bearerTokenResolver.resolve(request);
        if (StringUtils.isBlank(token)) {
            return ResponseData.success();
        }
        authenticationService.logout(token);
        return ResponseData.success();
    }

}
