package com.adastra.services;

import com.adastra.models.User;
import com.adastra.models.dtos.publication.PublicationItemDto;
import com.adastra.models.dtos.user.RegisterDto;
import com.adastra.models.enumerations.UserRoleEnum;
import com.adastra.repositories.PublicationRepository;
import com.adastra.repositories.UserRepository;
import com.adastra.repositories.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository ;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;
    private final UserDetailsService userDetailsService;

    public UserService(UserRepository userRepository, PublicationRepository publicationRepository, UserRoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.publicationRepository = publicationRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = modelMapper;
        this.userDetailsService = userDetailsService;
    }

    public void register(RegisterDto registerDto) {
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.addRole(roleRepository.findByName(UserRoleEnum.USER));
        userRepository.save(user);
        login(user.getUsername());
    }

    private void login(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public List<PublicationItemDto> getAllPublications(UUID id) {
        return publicationRepository.findByUserId(id).stream().map(PublicationItemDto::new).collect(Collectors.toList());
    }

    public String getUsernameById(UUID userId) {
        return userRepository.findById(userId).orElseThrow().getUsername();
    }
}
