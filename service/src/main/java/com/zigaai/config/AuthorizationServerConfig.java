package com.zigaai.config;

import com.zigaai.oauth2.config.BaseAuthorizationServerConfig;
import com.zigaai.oauth2.handler.OAuth2AuthenticationEntryPoint;
import com.zigaai.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import com.zigaai.oauth2.handler.OAuth2AuthorizationErrorHandler;
import com.zigaai.oauth2.service.RedisOAuth2AuthorizationConsentService;
import com.zigaai.oauth2.service.RedisOAuth2AuthorizationService;
import com.zigaai.security.filter.JwtFilter;
import com.zigaai.security.handler.DefaultAccessDeniedHandler;
import com.zigaai.security.handler.DefaultAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfig extends BaseAuthorizationServerConfig {

    public AuthorizationServerConfig(OAuth2AuthenticationEntryPoint oauth2AuthenticationEntryPoint,
                                     RegisteredClientRepository registeredClientRepository,
                                     RedisOAuth2AuthorizationService redisOAuth2AuthorizationService,
                                     OAuth2AuthorizationErrorHandler oauth2AuthorizationErrorHandler,
                                     OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
                                     DefaultAccessDeniedHandler defaultAccessDeniedHandler,
                                     DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint,
                                     JwtFilter jwtFilter,
                                     RedisOAuth2AuthorizationConsentService redisOAuth2AuthorizationConsentService) {
        super(oauth2AuthenticationEntryPoint, registeredClientRepository, redisOAuth2AuthorizationService, oauth2AuthorizationErrorHandler, oAuth2AuthenticationSuccessHandler, defaultAccessDeniedHandler, defaultAuthenticationEntryPoint, jwtFilter, redisOAuth2AuthorizationConsentService);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Override
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        return super.authorizationServerSecurityFilterChain(http);
    }
}
