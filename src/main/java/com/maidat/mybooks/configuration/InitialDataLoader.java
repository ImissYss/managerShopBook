package com.maidat.mybooks.configuration;

import com.maidat.mybooks.domain.Role;
import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.repository.RoleRepository;
import com.maidat.mybooks.domain.repository.UserRepository;
import com.maidat.mybooks.utils.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(alreadySetup)
            return;

        createRoleIfNotFound(UserRole.USER);
        createRoleIfNotFound(UserRole.ADMIN);
        createRoleIfNotFound(UserRole.MODERATOR);

        Role adminRole = roleRepository.findByName(UserRole.ADMIN);
        User user = new User();
        user.setDisplayName("Test");
        user.setLastName("Test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("test@test.com");
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user);

        alreadySetup = true;

    }

    @Transactional
    public Role createRoleIfNotFound(String name){
        Role role = roleRepository.findByName(name);
        if(role == null){
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }
}
