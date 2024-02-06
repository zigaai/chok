package com.zigaai.config;

import com.zigaai.infra.service.AuthenticationService;
import com.zigaai.security.enumeration.LoginType;
import com.zigaai.security.processor.LoginProcessor;
import com.zigaai.security.service.MultiAuthenticationUserDetailsService;
import com.zigaai.strategy.StrategyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration(proxyBeanMethods = false)
public class StrategyConfig {

    @Bean
    @DependsOn({"adminUserDetailsService", "normalUserDetailsService"})
    public StrategyFactory<String, MultiAuthenticationUserDetailsService> userDetailsServiceStrategy() {
        return new StrategyFactory<>(MultiAuthenticationUserDetailsService.class);
    }

    @Bean
    @DependsOn({"usernamePasswordProcessor"})
    public StrategyFactory<LoginType, LoginProcessor> loginTypeLoginProcessorStrategy() {
        return new StrategyFactory<>(LoginProcessor.class);
    }

    @Bean
    @DependsOn({"adminAuthenticationService", "normalUserAuthenticationService"})
    public StrategyFactory<String, AuthenticationService> authenticationServiceStrategy() {
        return new StrategyFactory<>(AuthenticationService.class);
    }

}
