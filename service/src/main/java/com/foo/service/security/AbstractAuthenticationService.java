package com.foo.service.security;

import com.foo.infra.security.SystemUser;
import com.foo.utils.SecurityUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public void logout() {
        SystemUser systemUser = SecurityUtil.currentUser();
        if (systemUser == null) {
            return;
        }
    }
}
