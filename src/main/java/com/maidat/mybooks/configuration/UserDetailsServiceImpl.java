package com.maidat.mybooks.configuration;

import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.repository.UserRepository;
import com.maidat.mybooks.utils.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetailsServiceImpl(){
        super();
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try{
            System.out.println(""+email);
            final User user = userRepository.findByEmail(email);
            if(user == null){
                throw new UsernameNotFoundException("No user found with username: "+email);
            }
            return new UserInfo(user, user.getEmail(), user.getPassword(), UserInfo.UserGrantedAuthorities.getAuthorities(user.getRoles()));
        }catch(final Exception e){
            throw new RuntimeException();
        }
    }
}
