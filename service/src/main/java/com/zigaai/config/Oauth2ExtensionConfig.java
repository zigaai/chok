package com.zigaai.config;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.zigaai.mapper.Oauth2RegisteredClientMapper;
import com.zigaai.oauth2.repo.DaoRegisteredClientRepository;
import com.zigaai.oauth2.service.JwtSaltValidator;
import com.zigaai.oauth2.service.RedisOAuth2AuthorizationConsentService;
import com.zigaai.oauth2.service.RedisOAuth2AuthorizationService;
import com.zigaai.security.properties.CustomSecurityProperties;
import com.zigaai.service.Oauth2AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
