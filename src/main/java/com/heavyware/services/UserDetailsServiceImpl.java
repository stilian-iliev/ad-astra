package com.heavyware.services;

import com.heavyware.models.User;
import com.heavyware.models.principal.AppUserDetails;
import com.heavyware.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!userRepository.existsByUsername(username)) throw new UsernameNotFoundException("Username not found!");
        //todo
        User user = userRepository.findByUsername(username);

        return new AppUserDetails();
    }
}
