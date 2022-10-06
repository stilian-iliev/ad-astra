package com.adastra.services;

import com.adastra.models.User;
import com.adastra.models.principal.AppUserDetails;
import com.adastra.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

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

        List<GrantedAuthority> authorities = user.getRoles()
                .stream().map(r -> "ROLE_" + r.getRoleName().name())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new AppUserDetails(user, authorities);
    }
}
