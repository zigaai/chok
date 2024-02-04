// package com.zigaai.config;
//
// import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
// import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.task.AsyncTaskExecutor;
// import org.springframework.core.task.support.TaskExecutorAdapter;
// import org.springframework.scheduling.annotation.EnableAsync;
//
// import java.util.concurrent.Executors;
//
// @EnableAsync
// @Configuration(proxyBeanMethods = false)
// @ConditionalOnProperty(value = "spring.thread-executor", havingValue = "virtual")
// public class ThreadConfig {
//
//     @Bean
//     public AsyncTaskExecutor applicationTaskExecutor() {
//         return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
//     }
//
//     @Bean
//     @SuppressWarnings("squid:S1452")
//     public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
//         return protocolHandler -> protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
//     }
//
// }
