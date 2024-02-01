package com.foo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zigaai.utils.JsonUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration(proxyBeanMethods = false)
public class ObjectMapperConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return JsonUtil.getInstance();
    }
}
