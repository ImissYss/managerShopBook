package com.maidat.mybooks.service;

import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.dto.UserDTO;
import com.maidat.mybooks.domain.repository.UserRepository;
import com.maidat.mybooks.utils.auth.AuthUser;
import com.maidat.mybooks.utils.dto.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    AuthUser auth;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDTO getUserToEdit(User user){
        UserDTO userDTO = ObjectMapperUtils.map(user, UserDTO.class);
        return userDTO;
    }

    @Transactional
    public User editUser(UserDTO userDTO,User user){
        Assert.notNull(userDTO, "User null");
        Assert.notNull(user, "User null");
        Assert.notNull(auth.getUserInfo(), "User null");

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setDescription(userDTO.getDescription());
        user.setDisplayName(userDTO.getDisplayName());

        User editUser = userRepo.save(user);

        return editUser;
    }

    @Transactional
    public User changePassword(String newPassword){
        User user = auth.getUserInfo().getUser();
        user.setPassword(passwordEncoder.encode(newPassword));

        return userRepo.save(user);
    }
}
