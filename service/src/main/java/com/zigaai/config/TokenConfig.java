package com.zigaai.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.zigaai.oauth2.config.BaseTokenConfig;
import com.zigaai.security.properties.CustomSecurityProperties;
import com.zigaai.security.service.AuthenticationService;
import com.zigaai.security.service.TokenCacheService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Configuration(proxyBeanMethods = false)
public class TokenConfig extends BaseTokenConfig {

    private final RedisTemplate<String, Object> redisTemplate;

    public TokenConfig(CustomSecurityProperties securityProperties,
                       AuthenticationService authenticationService,
                       RedisTemplate<String, Object> redisTemplate) {
        super(securityProperties, authenticationService);
        this.redisTemplate = redisTemplate;
    }

    @Bean
    @Override
    public OAuth2TokenGenerator<OAuth2Token> tokenGenerator(JwtEncoder jwtEncoder,
                                                            OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer) {
        return super.tokenGenerator(jwtEncoder, jwtCustomizer);
    }

    @Bean
    @Override
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return super.jwtCustomizer();
    }

    @Bean
    @Override
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return super.jwtEncoder(jwkSource);
    }

    @Bean
    @Override
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return super.jwtDecoder(jwkSource);
    }

    @Bean
    @Override
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException, InvalidKeySpecException {
        return super.jwkSource();
    }

    @Bean
    public TokenCacheService tokenCacheService() {
        return new TokenCacheService(redisTemplate);
    }
}
