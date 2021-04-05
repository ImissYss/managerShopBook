package com.maidat.mybooks.utils.auth;

import com.maidat.mybooks.utils.user.UserInfo;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUserImpl implements AuthUser{

    private AuthenticationTrustResolverImpl authenticationTrustResolver = new AuthenticationTrustResolverImpl();

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public boolean isLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.isAuthenticated() && !isAnonymous(auth))
            return true;
        return false;
    }

    @Override
    public UserInfo getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(isLoggedIn())
            return (UserInfo) auth.getPrincipal();
        return null;
    }

    @Override
    public boolean isAnonymous() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return isAnonymous(auth);
    }

    public boolean isAnonymous(Authentication a){
        return authenticationTrustResolver.isAnonymous(a);
    }
}
