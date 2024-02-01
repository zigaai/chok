package com.foo.config;

import com.zigaai.enumeration.LoginType;
import com.zigaai.filter.JwtFilter;
import com.zigaai.filter.LoginAuthenticationFilter;
import com.zigaai.handler.DefaultAccessDeniedHandler;
import com.zigaai.handler.DefaultAuthenticationEntryPoint;
import com.zigaai.processor.LoginProcessor;
import com.zigaai.properties.CustomSecurityProperties;
import com.zigaai.provider.DaoMultiAuthenticationProvider;
import com.zigaai.service.MultiAuthenticationUserDetailsService;
import com.zigaai.strategy.StrategyFactory;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.HeaderWriterFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(CustomSecurityProperties.class)
public class SecurityConfig {

    private final CustomSecurityProperties securityProperties;

    private final StrategyFactory<LoginType, LoginProcessor> loginTypeLoginProcessorStrategy;

    private final StrategyFactory<String, MultiAuthenticationUserDetailsService> userDetailsServiceStrategy;

    private final DefaultAccessDeniedHandler defaultAccessDeniedHandler;

    private final DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;

    private final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;

    private final RedisTemplate<String, Object> redisTemplate;

    @Bean
    @SuppressWarnings("squid:S125")
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = buildAuthenticationManager();
        LoginAuthenticationFilter loginAuthenticationFilter = buildLoginFilter(authenticationManager);
        http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .requestCache(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(config ->
                        config
                                .dispatcherTypeMatchers(DispatcherType.ERROR)
                                .permitAll()
                                .requestMatchers(securityProperties.getIgnoreUrls().toArray(String[]::new))
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .exceptionHandling(config -> config
                        .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                        .accessDeniedHandler(defaultAccessDeniedHandler))
                .authenticationManager(authenticationManager)
                .addFilterAfter(loginAuthenticationFilter, HeaderWriterFilter.class)
                .addFilterAt(new JwtFilter(userDetailsServiceStrategy, securityProperties), HeaderWriterFilter.class);
        // .oauth2ResourceServer(resourceServer -> resourceServer
        //         .jwt(Customizer.withDefaults())
        //         .authenticationEntryPoint(defaultAuthenticationEntryPoint)
        //         .accessDeniedHandler(defaultAccessDeniedHandler)
        // );
        return http.build();
    }

    private LoginAuthenticationFilter buildLoginFilter(AuthenticationManager authenticationManager) {
        return new LoginAuthenticationFilter(loginTypeLoginProcessorStrategy,
                authenticationManager,
                securityProperties,
                jackson2HttpMessageConverter,
                redisTemplate);
    }

    private AuthenticationManager buildAuthenticationManager() {
        DaoMultiAuthenticationProvider daoMultiAuthenticationProvider = new DaoMultiAuthenticationProvider(userDetailsServiceStrategy, securityProperties);
        return new ProviderManager(daoMultiAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
