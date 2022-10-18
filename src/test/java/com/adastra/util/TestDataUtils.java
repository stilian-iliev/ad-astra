package com.adastra.util;

import com.adastra.models.Publication;
import com.adastra.models.User;
import com.adastra.models.UserRole;
import com.adastra.models.enumerations.UserRoleEnum;
import com.adastra.repositories.PublicationRepository;
import com.adastra.repositories.UserRepository;
import com.adastra.repositories.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestDataUtils {
    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final PublicationRepository publicationRepository;
    private final PasswordEncoder passwordEncoder;

    public TestDataUtils(UserRepository userRepository, UserRoleRepository roleRepository, PublicationRepository publicationRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.publicationRepository = publicationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void initRoles() {
        for (UserRoleEnum role : UserRoleEnum.values()) {

            if (!roleRepository.existsByName(role)){
                UserRole userRole = new UserRole(role);
                roleRepository.save(userRole);
            }
        }
    }

    public User createTestUser(String username, String password) {
        User user = createUser(username, password);
        return userRepository.save(user);
    }

    public User createTestAdmin(String username, String password) {
        User user = createUser(username, password);
        user.addRole(roleRepository.findByName(UserRoleEnum.ADMIN));
        return userRepository.save(user);
    }

    public User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.addRole(roleRepository.findByName(UserRoleEnum.USER));
        return user;
    }

    public Publication createTestPublication(String title, String image, User user) {
        Publication publication = new Publication();
        publication.setTitle(title);
        publication.setImage(image);
        publication.setUser(user);
        return publicationRepository.save(publication);
    }

    public void cleanUpDatabase() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        publicationRepository.deleteAll();
    }
}
