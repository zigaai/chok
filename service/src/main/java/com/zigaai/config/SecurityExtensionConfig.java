package com.zigaai.config;

import com.zigaai.security.filter.JwtFilter;
import com.zigaai.security.handler.DefaultAccessDeniedHandler;
import com.zigaai.security.handler.DefaultAuthenticationEntryPoint;
import com.zigaai.security.properties.CustomSecurityProperties;
import com.zigaai.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class SecurityExtensionConfig {

    private final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;

    private final AuthenticationService authenticationService;

    private final CustomSecurityProperties securityProperties;

    @Bean
    public DefaultAccessDeniedHandler defaultAccessDeniedHandler() {
        return new DefaultAccessDeniedHandler(jackson2HttpMessageConverter);
    }

    @Bean
    public DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint() {
        return new DefaultAuthenticationEntryPoint(jackson2HttpMessageConverter);
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(authenticationService, securityProperties.getIgnoreUrls(), securityProperties.getKeyPairs());
    }
}
