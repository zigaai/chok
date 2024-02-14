package com.zigaai.controller;

import com.zigaai.model.common.ResponseData;
import com.zigaai.security.converter.SystemUserConvertor;
import com.zigaai.security.model.SystemUser;
import com.zigaai.security.model.SystemUserVO;
import com.zigaai.security.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public ResponseData<Object> getInfo() {
        SystemUser systemUser = SecurityUtil.currentUser();
        SystemUserVO info = SystemUserConvertor.INSTANCE.toVO(systemUser);
        return ResponseData.success(info);
    }

    // /**
    //  * 登出
    //  */
    // @PostMapping("/logout")
    // public ResponseData<Integer> logout(HttpServletRequest request) {
    //     int count = authenticationHandler.logout(SecurityUtil.getTokenVal(request));
    //     return ResponseData.success(count);
    // }

    // /**
    //  * 刷新Token
    //  *
    //  * @param refreshToken 刷新token
    //  */
    // @PostMapping("/refreshToken")
    // public ResponseData<UPMSToken> refreshToken(@RequestParam("refreshToken") String refreshToken) throws JsonProcessingException, JOSEException, NoSuchAlgorithmException, InvalidKeySpecException {
    //     UPMSToken token = authenticationHandler.refreshToken(refreshToken);
    //     return ResponseData.success(token);
    // }

}
