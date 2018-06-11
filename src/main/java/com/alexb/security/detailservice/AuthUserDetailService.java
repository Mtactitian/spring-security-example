package com.alexb.security.detailservice;

import com.alexb.model.AuthorizedUser;
import com.alexb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUserDetailService implements UserDetailsService {

    private final ApplicationEventPublisher applicationEventPublisher;

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final AuthorizedUser authorizedUser = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exsits"));

        applicationEventPublisher.publishEvent(authorizedUser);

        return authorizedUser;
    }
}