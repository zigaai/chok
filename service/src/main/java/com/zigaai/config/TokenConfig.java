package com.zigaai.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.zigaai.oauth2.config.BaseTokenConfig;
import com.zigaai.security.properties.CustomSecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

@Configuration(proxyBeanMethods = false)
public class TokenConfig extends BaseTokenConfig {

    public TokenConfig(CustomSecurityProperties securityProperties) {
        super(securityProperties);
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
}
