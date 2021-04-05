package com.maidat.mybooks.service;

import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.dto.UserSignUpDTO;
import com.maidat.mybooks.domain.repository.RoleRepository;
import com.maidat.mybooks.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class UserSignUpServiceImpl {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User registerNewUserAccount(UserSignUpDTO accountDto) throws Exception {
        if(emailExist(accountDto.getEmail())){
            throw new Exception(
                    "there is an account with that email address"
            );
        }
        User user = new User();
        user.setFirstName(accountDto.getName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setJoinDate(LocalDateTime.now());
        user.setAddress(accountDto.getAddress());
        user.setPhone(accountDto.getPhone());

        return repository.save(user);
    }

    private boolean emailExist(String email){
        User user = repository.findByEmail(email);
        if(user != null){
            return true;
        }
        return false;
    }
}
