// package com.zigaai.config;
//
// import com.zigaai.oauth2.config.BaseSecurityConfig;
// import com.zigaai.security.handler.DefaultAccessDeniedHandler;
// import com.zigaai.security.handler.DefaultAuthenticationEntryPoint;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.annotation.Order;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;
//
// @Configuration
// public class SecurityConfig extends BaseSecurityConfig {
//
//     public SecurityConfig(DefaultAccessDeniedHandler defaultAccessDeniedHandler,
//                           DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint) {
//         super(defaultAccessDeniedHandler, defaultAuthenticationEntryPoint);
//     }
//
//     @Bean
//     @Order(3)
//     @Override
//     public SecurityFilterChain defaultResourceSecurityFilterChain(HttpSecurity http) throws Exception {
//         return super.defaultResourceSecurityFilterChain(http);
//     }
// }
