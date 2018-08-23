package com.example.demo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class SSUserDetailsService implements UserDetailsService{

    private UserRepository userRepository;

    public SSUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        try {
            AppUser appUser = userRepository.findByUsername(username);
            if (appUser == null){
                return null;
            }

            return new org.springframework.security.core.userdetails.User(
                    appUser.getUsername(),
                    appUser.getPassword(),
                    getAuthorities(appUser));
        } catch (Exception e){
            throw new UsernameNotFoundException("User not found");
        }

    }

    private Set<GrantedAuthority> getAuthorities(AppUser appUser){
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for(AppRole appRole : appUser.getAppRoles()){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(appRole.getAppRole());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }

}
