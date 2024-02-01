package com.foo.config;

import com.foo.infra.security.AuthenticationService;
import com.zigaai.enumeration.LoginType;
import com.zigaai.processor.LoginProcessor;
import com.zigaai.service.MultiAuthenticationUserDetailsService;
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
