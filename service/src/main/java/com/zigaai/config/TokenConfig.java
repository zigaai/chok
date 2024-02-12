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
import com.zigaai.oauth2.config.BaseTokenConfig;
import com.zigaai.oauth2.service.JwtSaltValidator;
import com.zigaai.security.properties.CustomSecurityProperties;
import com.zigaai.service.Oauth2AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration(proxyBeanMethods = false)
public class TokenConfig extends BaseTokenConfig {

    public TokenConfig(CustomSecurityProperties securityProperties,
                       Oauth2AuthenticationService oauth2AuthenticationService) {
        super(securityProperties, oauth2AuthenticationService);
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
}
