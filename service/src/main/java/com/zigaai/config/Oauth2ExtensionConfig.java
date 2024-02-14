package com.zigaai.config;

import com.zigaai.mapper.Oauth2RegisteredClientMapper;
import com.zigaai.oauth2.repo.DaoRegisteredClientRepository;
import com.zigaai.oauth2.service.RedisOAuth2AuthorizationConsentService;
import com.zigaai.oauth2.service.RedisOAuth2AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

@Configuration
@RequiredArgsConstructor
public class Oauth2ExtensionConfig {

    private final RedisTemplate<String, Object> redisTemplate;

    private final Oauth2RegisteredClientMapper oauth2RegisteredClientMapper;

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        return new DaoRegisteredClientRepository(redisTemplate, oauth2RegisteredClientMapper);
    }

    @Bean
    public RedisOAuth2AuthorizationService redisOAuth2AuthorizationService() {
        return new RedisOAuth2AuthorizationService(registeredClientRepository(), redisTemplate);
    }

    @Bean
    public RedisOAuth2AuthorizationConsentService redisOAuth2AuthorizationConsentService() {
        return new RedisOAuth2AuthorizationConsentService(redisTemplate);
    }
}
