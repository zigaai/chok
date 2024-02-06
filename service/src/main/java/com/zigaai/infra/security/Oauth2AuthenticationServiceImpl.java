package com.zigaai.infra.security;

import com.zigaai.infra.service.AuthenticationService;
import com.zigaai.service.Oauth2AuthenticationService;
import com.zigaai.strategy.StrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Oauth2AuthenticationServiceImpl implements Oauth2AuthenticationService {

    private final StrategyFactory<String, AuthenticationService> authenticationServiceStrategy;

    @Override
    public String getSaltByUsername(String userType, String username) {
        AuthenticationService service = authenticationServiceStrategy.getStrategy(userType);
        if (service == null) {
            return null;
        }
        return service.getSaltByUsername(username);
    }

}
