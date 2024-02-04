package com.zigaai.config;

import com.zigaai.infra.security.AuthenticationService;
import com.zigaai.security.enumeration.LoginType;
import com.zigaai.security.processor.LoginProcessor;
import com.zigaai.security.service.MultiAuthenticationUserDetailsService;
import com.zigaai.strategy.StrategyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class StrategyConfig {

    @Bean
    public StrategyFactory<String, MultiAuthenticationUserDetailsService> userDetailsServiceStrategy() {
        return new StrategyFactory<>(MultiAuthenticationUserDetailsService.class);
    }

    @Bean
    public StrategyFactory<LoginType, LoginProcessor> loginTypeLoginProcessorStrategy() {
        return new StrategyFactory<>(LoginProcessor.class);
    }

    @Bean
    public StrategyFactory<String, AuthenticationService> authenticationServiceStrategy() {
        return new StrategyFactory<>(AuthenticationService.class);
    }

}
