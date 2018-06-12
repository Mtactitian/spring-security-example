package com.alexb.security;

import com.alexb.model.AuthorizedUser;
import com.alexb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthorizedUserContext implements UserContext {

    private final UserRepository userRepository;

    @Override
    public void setCurrentUser(AuthorizedUser authorizedUser) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(authorizedUser,
                authorizedUser.getPassword(), authorizedUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    @Transactional
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public void deleteUser(String name) {
        userRepository.deleteById(name);
    }
}