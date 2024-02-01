package com.foo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.foo.service.security.AuthenticationHandler;
import com.nimbusds.jose.JOSEException;
import com.zigaai.model.SystemUserVO;
import com.zigaai.model.UPMSToken;
import com.zigaai.model.common.ResponseData;
import com.zigaai.utils.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 权限相关接口
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class AuthenticationController {

    private final AuthenticationHandler authenticationHandler;

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public ResponseData<SystemUserVO> getInfo() {
        SystemUserVO info = authenticationHandler.getInfo();
        return ResponseData.success(info);
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public ResponseData<Integer> logout(HttpServletRequest request) {
        int count = authenticationHandler.logout(SecurityUtil.getTokenVal(request));
        return ResponseData.success(count);
    }

    /**
     * 刷新Token
     *
     * @param refreshToken 刷新token
     */
    @PostMapping("/refreshToken")
    public ResponseData<UPMSToken> refreshToken(@RequestParam String refreshToken) throws JsonProcessingException, JOSEException {
        UPMSToken token = authenticationHandler.refreshToken(refreshToken);
        return ResponseData.success(token);
    }

}
