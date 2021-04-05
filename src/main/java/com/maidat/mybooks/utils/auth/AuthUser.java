package com.maidat.mybooks.utils.auth;

import com.maidat.mybooks.utils.user.UserInfo;
import org.springframework.security.core.Authentication;

public interface AuthUser {

    Authentication getAuthentication();
    boolean isLoggedIn();
    UserInfo getUserInfo();
    boolean isAnonymous();
}
