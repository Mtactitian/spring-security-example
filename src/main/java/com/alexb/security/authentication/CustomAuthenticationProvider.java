package com.alexb.security.authentication;

import com.alexb.model.AuthorizedUser;
import com.alexb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication.getPrincipal() instanceof AuthorizedUser)) {
            String name = authentication.getName();
            AuthorizedUser authorizedUser = userRepository.findById(name)
                    .orElseThrow(() -> new UsernameNotFoundException(name + " not found."));

            String password = authentication.getCredentials().toString();
            if (!passwordEncoder.matches(password, authorizedUser.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }
            return new UsernamePasswordAuthenticationToken(authorizedUser, authorizedUser.getPassword(), authorizedUser.getAuthorities());
        }
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}