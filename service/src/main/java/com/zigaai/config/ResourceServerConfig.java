package com.zigaai.config;

import com.zigaai.security.config.BaseResourceServerConfig;
import com.zigaai.security.enumeration.LoginType;
import com.zigaai.security.filter.LoginAuthenticationFilter;
import com.zigaai.security.handler.DefaultAccessDeniedHandler;
import com.zigaai.security.handler.DefaultAuthenticationEntryPoint;
import com.zigaai.security.processor.LoginProcessor;
import com.zigaai.security.properties.CustomSecurityProperties;
import com.zigaai.security.provider.DaoMultiAuthenticationProvider;
import com.zigaai.security.service.AuthenticationService;
import com.zigaai.security.service.TokenCacheService;
import com.zigaai.strategy.StrategyFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.HeaderWriterFilter;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(CustomSecurityProperties.class)
public class ResourceServerConfig extends BaseResourceServerConfig {

    private final StrategyFactory<LoginType, LoginProcessor> loginTypeLoginProcessorStrategy;

    private final CustomSecurityProperties securityProperties;

    private final AuthenticationService authenticationService;

    private final TokenCacheService tokenCacheService;

    private final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;

    public ResourceServerConfig(MappingJackson2HttpMessageConverter jackson2HttpMessageConverter,
                                CustomSecurityProperties securityProperties,
                                DefaultAccessDeniedHandler defaultAccessDeniedHandler,
                                DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint,
                                StrategyFactory<LoginType, LoginProcessor> loginTypeLoginProcessorStrategy,
                                AuthenticationService authenticationService,
                                TokenCacheService tokenCacheService) {
        super(defaultAccessDeniedHandler, defaultAuthenticationEntryPoint);
        this.loginTypeLoginProcessorStrategy = loginTypeLoginProcessorStrategy;
        this.securityProperties = securityProperties;
        this.authenticationService = authenticationService;
        this.tokenCacheService = tokenCacheService;
        this.jackson2HttpMessageConverter = jackson2HttpMessageConverter;
    }

    @Bean
    @Order(2)
    @Override
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return super.defaultSecurityFilterChain(http);
    }

    @Override
    protected void postProcessAfterInitialization(HttpSecurity http) {
        AuthenticationManager authenticationManager = buildAuthenticationManager();
        LoginAuthenticationFilter loginAuthenticationFilter = buildLoginFilter(authenticationManager);
        http.addFilterAfter(loginAuthenticationFilter, HeaderWriterFilter.class);
    }

    public LoginAuthenticationFilter buildLoginFilter(AuthenticationManager authenticationManager) {
        return new LoginAuthenticationFilter(loginTypeLoginProcessorStrategy,
                authenticationManager,
                securityProperties,
                jackson2HttpMessageConverter,
                tokenCacheService);
    }

    public AuthenticationManager buildAuthenticationManager() {
        DaoMultiAuthenticationProvider daoMultiAuthenticationProvider = new DaoMultiAuthenticationProvider(authenticationService, securityProperties);
        return new ProviderManager(daoMultiAuthenticationProvider);
    }

}
