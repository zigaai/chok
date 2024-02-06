package com.zigaai.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.JOSEException;
import com.zigaai.model.common.ResponseData;
import com.zigaai.model.security.UPMSToken;
import com.zigaai.security.utils.SecurityUtil;
import com.zigaai.service.security.AuthenticationHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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
    public ResponseData<Object> getInfo() {
        // SystemUserVO info = authenticationHandler.getInfo();
        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseData.success(authentication);
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
    public ResponseData<UPMSToken> refreshToken(@RequestParam("refreshToken") String refreshToken) throws JsonProcessingException, JOSEException, NoSuchAlgorithmException, InvalidKeySpecException {
        UPMSToken token = authenticationHandler.refreshToken(refreshToken);
        return ResponseData.success(token);
    }

}
