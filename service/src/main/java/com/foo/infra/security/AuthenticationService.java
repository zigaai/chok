package com.foo.infra.security;

import com.zigaai.model.SystemUser;
import com.zigaai.strategy.Strategy;

public interface AuthenticationService extends Strategy<String> {

    int logout(SystemUser systemUser);

    String getSaltByUsername(String username);

    int refreshSalt(String username);

}
