// package com.zigaai.service.security;
//
// import com.zigaai.mapper.AdminMapper;
// import com.zigaai.model.entity.Admin;
// import com.zigaai.security.service.TokenCacheService;
// import org.springframework.stereotype.Component;
//
// @Component
// public class AdminAuthenticationService extends AbstractAuthenticationService<Admin> {
//
//     public AdminAuthenticationService(AdminMapper adminMapper,
//                                       TokenCacheService tokenCacheService) {
//         super(adminMapper, tokenCacheService);
//     }
//
//     @Override
//     public String getKey() {
//         return "admin";
//     }
//
//
// }
