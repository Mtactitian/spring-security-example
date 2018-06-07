package com.alexb.service;

import com.alexb.model.Authority;
import com.alexb.model.AuthorizedUser;
import com.alexb.model.dto.UserRegistrationDto;
import com.alexb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static com.alexb.model.enums.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public AuthorizedUser registerUser(UserRegistrationDto userRegistrationDto) {
        if (userExists(userRegistrationDto.getUsername())) {
            throw new RuntimeException("User already exists");
        }

        AuthorizedUser authorizedUser = new AuthorizedUser();
        authorizedUser.setUsername(userRegistrationDto.getUsername());
        authorizedUser.setPassword(bCryptPasswordEncoder.encode(userRegistrationDto.getPassword()));
        authorizedUser.setEnabled(true);

        Authority authority = new Authority();
        authority.setAuthority(ROLE_USER);
        authority.setAuthorizedUser(authorizedUser);

        authorizedUser.setAuthorities(Collections.singletonList(authority));
        return userRepository.save(authorizedUser);
    }

    private boolean userExists(final String username) {
        return userRepository.existsById(username);
    }
}
