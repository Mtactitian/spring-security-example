package com.alexb.security;

import com.alexb.model.AuthorizedUser;
import com.alexb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizedUserContext implements UserContext {

    private final UserRepository userRepository;

    @Override
    public AuthorizedUser getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return (AuthorizedUser) authentication.getPrincipal();
        }
        return null;
    }

    @Override
    public void setCurrentUser(AuthorizedUser authorizedUser) {
        Authentication authentication = new
                UsernamePasswordAuthenticationToken(authorizedUser, authorizedUser.getPassword(), authorizedUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void deleteUser(String name) {
        userRepository.deleteById(name);
    }
}