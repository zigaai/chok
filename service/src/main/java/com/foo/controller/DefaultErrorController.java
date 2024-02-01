package com.foo.controller;

import com.zigaai.exception.JwtExpiredException;
import com.zigaai.exception.JwtInvalidException;
import com.zigaai.exception.RefreshTokenExpiredException;
import com.zigaai.model.common.ResponseData;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class DefaultErrorController extends AbstractErrorController {

    private final ErrorAttributes errorAttributes;

    public DefaultErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping
    public ResponseEntity<ResponseData<Void>> error(HttpServletRequest request) {
        Throwable error = errorAttributes.getError(new ServletWebRequest(request));
        String msg = "未知错误, 请联系管理员处理";
        int code = (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (error != null) {
            if (error instanceof JwtInvalidException
                    || error instanceof JwtExpiredException
                    || error instanceof UsernameNotFoundException) {
                code = HttpStatus.UNAUTHORIZED.value();
                msg = error.getMessage();
            }
            if (error instanceof RefreshTokenExpiredException) {
                code = HttpStatus.UNAUTHORIZED.value();
                msg = error.getMessage();
                return new ResponseEntity<>(ResponseData.needLogin(msg), HttpStatusCode.valueOf(code));
            }
        }
        ResponseData<Void> res;
        if (code == HttpStatus.NOT_FOUND.value()) {
            res = ResponseData.notFound();
        } else if (code == HttpStatus.UNAUTHORIZED.value()) {
            res = ResponseData.unauthorized(msg);
        } else {
            res = ResponseData.unknownError(msg);
        }
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(code));
    }

}
