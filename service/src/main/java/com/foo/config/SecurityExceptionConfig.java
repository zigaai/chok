package com.foo.config;

import com.zigaai.handler.DefaultAccessDeniedHandler;
import com.zigaai.handler.DefaultAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class SecurityExceptionConfig {

    private final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;

    @Bean
    public DefaultAccessDeniedHandler defaultAccessDeniedHandler() {
        return new DefaultAccessDeniedHandler(jackson2HttpMessageConverter);
    }

    @Bean
    public DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint() {
        return new DefaultAuthenticationEntryPoint(jackson2HttpMessageConverter);
    }

}
