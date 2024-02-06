package com.zigaai.config;

import com.zigaai.oauth2.config.BaseResourceServerConfig;
import com.zigaai.security.enumeration.LoginType;
import com.zigaai.security.handler.DefaultAccessDeniedHandler;
import com.zigaai.security.handler.DefaultAuthenticationEntryPoint;
import com.zigaai.security.processor.LoginProcessor;
import com.zigaai.security.properties.CustomSecurityProperties;
import com.zigaai.security.service.MultiAuthenticationUserDetailsService;
import com.zigaai.security.service.TokenCacheService;
import com.zigaai.strategy.StrategyFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(CustomSecurityProperties.class)
public class ResourceServerConfig extends BaseResourceServerConfig {

    public ResourceServerConfig(MappingJackson2HttpMessageConverter jackson2HttpMessageConverter,
                                CustomSecurityProperties securityProperties,
                                DefaultAccessDeniedHandler defaultAccessDeniedHandler,
                                DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint,
                                StrategyFactory<LoginType, LoginProcessor> loginTypeLoginProcessorStrategy,
                                StrategyFactory<String, MultiAuthenticationUserDetailsService> userDetailsServiceStrategy,
                                TokenCacheService tokenCacheService) {
        super(jackson2HttpMessageConverter, securityProperties, defaultAccessDeniedHandler, defaultAuthenticationEntryPoint, loginTypeLoginProcessorStrategy, userDetailsServiceStrategy, tokenCacheService);
    }

    @Bean
    @Order(2)
    @Override
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return super.defaultSecurityFilterChain(http);
    }

}
