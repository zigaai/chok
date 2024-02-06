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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class Oauth2ExtensionConfig {

    private final RedisTemplate<String, Object> redisTemplate;

    private final Oauth2RegisteredClientMapper oauth2RegisteredClientMapper;

    private final CustomSecurityProperties securityProperties;

    private final Oauth2AuthenticationService oauth2AuthenticationService;

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

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        Set<JWSAlgorithm> jwsAlgs = new HashSet<>();
        jwsAlgs.addAll(JWSAlgorithm.Family.RSA);
        jwsAlgs.addAll(JWSAlgorithm.Family.EC);
        jwsAlgs.addAll(JWSAlgorithm.Family.HMAC_SHA);
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        JWSKeySelector<SecurityContext> jwsKeySelector =
                new JWSVerificationKeySelector<>(jwsAlgs, jwkSource);
        jwtProcessor.setJWSKeySelector(jwsKeySelector);
        // Override the default Nimbus claims set verifier as NimbusJwtDecoder handles it instead
        jwtProcessor.setJWTClaimsSetVerifier((claims, context) -> {
        });
        NimbusJwtDecoder decoder = new NimbusJwtDecoder(jwtProcessor);
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(Arrays.asList(new JwtTimestampValidator(), new JwtSaltValidator(oauth2AuthenticationService))));
        return decoder;
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPair keyPairs = securityProperties.getKeyPairs();
        RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) keyPairs.getPublic())
                .privateKey(keyPairs.getPrivate())
                // .keyID(uuid)
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }
}
