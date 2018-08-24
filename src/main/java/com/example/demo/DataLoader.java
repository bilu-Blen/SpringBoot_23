package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... strings) throws Exception {
        AppRole adminRole = roleRepository.findByAppRole("ADMIN");
        AppRole userRole = roleRepository.findByAppRole("USER");
        roleRepository.save(new AppRole("USER"));
        roleRepository.save(new AppRole("ADMIN"));





        AppUser appUser = new AppUser("bob@bob.com", "bob", "Bob", "Bobberson", true, "bob");
         appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        userRepository.save(appUser);
        appUser.setAppRoles(Arrays.asList(adminRole));
        userRepository.save(appUser);

        appUser = new AppUser("jim@jim.com", "jim", "jim", "Jimmerson", true, "jim");
        appUser.setAppRoles(Arrays.asList(userRole));
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        userRepository.save(appUser);

        appUser = new AppUser("admin@admin.com", "pass", "Admin", "User", true, "admin");
        appUser.setAppRoles(Arrays.asList(adminRole));
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        userRepository.save(appUser);

        appUser = new AppUser("sam@ev.com", "pass", "Sam", "Everyman", true, "sam");
        appUser.setAppRoles(Arrays.asList(userRole, adminRole));
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        userRepository.save(appUser);


    }



}
