package com.adastra.init;

import com.adastra.models.User;
import com.adastra.models.enumerations.UserRoleEnum;
import com.adastra.repositories.UserRepository;
import com.adastra.repositories.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInit implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository roleRepository;

    public AdminInit(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        if (userRepository.count() == 0) {
//            User admin = new User();
//            admin.setUsername("admin");
//            admin.setPassword(passwordEncoder.encode("topsecret"));
//            admin.addRole(roleRepository.findByName(UserRoleEnum.USER));
//            admin.addRole(roleRepository.findByName(UserRoleEnum.ADMIN));
//            userRepository.save(admin);
//        }
    }
}
