package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Arrays;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public AppUser findNameEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Long countByEmail(String email){
        return userRepository.countByEmail(email);
    }

    public AppUser findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void saveUser(AppUser appUser){
        appUser.setAppRoles(Arrays.asList(roleRepository.findByAppRole("USER")));
        appUser.setEnabled(true);
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        userRepository.save(appUser);
    }

    public void saveAdmin(AppUser appUser){
        appUser.setAppRoles(Arrays.asList(roleRepository.findByAppRole("ADMIN")));
        appUser.setEnabled(true);
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        userRepository.save(appUser);
    }

}

