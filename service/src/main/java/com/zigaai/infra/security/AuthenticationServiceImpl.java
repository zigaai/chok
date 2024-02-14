package com.zigaai.infra.security;

import com.zigaai.security.model.SystemUser;
import com.zigaai.security.service.AuthenticationService;
import com.zigaai.security.service.BaseAuthenticationGrpcServerService;
import com.zigaai.security.service.MultiAuthenticationUserDetailsService;
import com.zigaai.strategy.StrategyFactory;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class AuthenticationServiceImpl extends BaseAuthenticationGrpcServerService implements AuthenticationService {

    private final StrategyFactory<String, MultiAuthenticationUserDetailsService> userDetailsServiceStrategy;

    @Override
    public String getSalt(String userType, String username) {
        return userDetailsServiceStrategy.getStrategy(userType).getSaltByUsername(username);
    }

    @Override
    public SystemUser loadUserByUsername(String userType, String username) {
        return (SystemUser) userDetailsServiceStrategy.getStrategy(userType).loadUserByUsername(username);
    }

}
