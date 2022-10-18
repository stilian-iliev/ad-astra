package com.adastra.util;

import com.adastra.models.User;
import com.adastra.models.principal.AppUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class TestUserDataService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        user.setUsername(DefaultUser.USERNAME);
        user.setPassword(DefaultUser.PASSWORD);
        return new AppUserDetails(user,Collections.emptyList());
    }
}
