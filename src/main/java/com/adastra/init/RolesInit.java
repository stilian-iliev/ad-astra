package com.adastra.init;

import com.adastra.models.UserRole;
import com.adastra.models.enumerations.UserRoleEnum;
import com.adastra.repositories.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RolesInit implements CommandLineRunner {
    private final UserRoleRepository roleRepository;

    public RolesInit(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for (UserRoleEnum role : UserRoleEnum.values()) {

            if (!roleRepository.existsByName(role)){
                UserRole userRole = new UserRole(role);
                roleRepository.save(userRole);
            }
        }
    }
}
