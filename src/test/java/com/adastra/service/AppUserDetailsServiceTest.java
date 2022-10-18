package com.adastra.service;

import com.adastra.models.User;
import com.adastra.models.UserRole;
import com.adastra.models.enumerations.UserRoleEnum;
import com.adastra.models.principal.AppUserDetails;
import com.adastra.repositories.UserRepository;
import com.adastra.services.UserDetailsServiceImpl;
import com.adastra.util.DefaultUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppUserDetailsServiceTest {
    @Mock
    private UserRepository mockUserRepo;

    private UserDetailsServiceImpl toTest;


    @BeforeEach
    void setUp() {
        toTest = new UserDetailsServiceImpl(mockUserRepo);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        User testUser = new User();
        testUser.setUsername(DefaultUser.USERNAME);
        testUser.setPassword(DefaultUser.PASSWORD);
        testUser.addRole(new UserRole(UserRoleEnum.USER));
        testUser.addRole(new UserRole(UserRoleEnum.ADMIN));

        when(mockUserRepo.existsByUsername(testUser.getUsername()))
                .thenReturn(true);
        when(mockUserRepo.findByUsername(testUser.getUsername())).
                thenReturn(testUser);

        AppUserDetails appUserDetails = (AppUserDetails) toTest.loadUserByUsername(testUser.getUsername());

        Assertions.assertEquals(testUser.getUsername(),
                appUserDetails.getUsername());

        Assertions.assertEquals(testUser.getPassword(),
                appUserDetails.getPassword());

        var authorities = appUserDetails.getAuthorities();

        Assertions.assertEquals(2, authorities.size());

        var authoritiesIter = authorities.iterator();

        Assertions.assertEquals("ROLE_" + UserRoleEnum.USER.name(),
                authoritiesIter.next().getAuthority());
        Assertions.assertEquals("ROLE_" + UserRoleEnum.ADMIN.name(),
                authoritiesIter.next().getAuthority());
    }

    @Test
    void testLoadUserByUsername_UserDoesNotExist() {
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername("invalid")
        );
    }
}
