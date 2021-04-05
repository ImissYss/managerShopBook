package com.maidat.mybooks.utils.user;

import com.maidat.mybooks.domain.Role;
import com.maidat.mybooks.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserInfo extends org.springframework.security.core.userdetails.User {

    private User user;
    public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserInfo(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public UserInfo(User user, String username, String password, Collection<? extends GrantedAuthority> authorities){
        super(username,password,true, true, true, true, authorities);
        setUser(user);
    }

    public static class UserGrantedAuthorities{
        public static Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles){
            final List<GrantedAuthority> authorities = new ArrayList<>();
            for(final Role role : roles){
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            return authorities;
        }
    }

    private void init(){

    }

    public boolean isAdmin(){
        return user.getRoles().stream().anyMatch(r -> r.getName().equals(UserRole.ADMIN));
    }

    public boolean isModerator(){
        return user.getRoles().stream().anyMatch(r -> r.getName().equals(UserRole.MODERATOR));
    }

    public boolean isAdminOrModerator(){
        return isAdmin() || isModerator();
    }

    public String getAllUserRoles(){
        String output = "";

        for(Role role : user.getRoles()){
            output += role.getName() + " ";
        }
        return output.trim();
    }

    public User getUser(){
        return user;
    }
    public void setUser(User user){
        Assert.notNull(user,"Null argument");
        this.user = user;
    }
}
