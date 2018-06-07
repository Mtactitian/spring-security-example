package com.alexb.security.remembermeservice;

import com.alexb.model.PersistedToken;
import com.alexb.repository.RememberMeTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class PersistedTokenService implements PersistentTokenRepository {

    private final RememberMeTokenRepository tokenRepository;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        tokenRepository.save(new PersistedToken(token));
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        tokenRepository.findById(series)
                .ifPresent(persistedToken -> {
                    persistedToken.setTokenValue(tokenValue);
                    persistedToken.setLastUsed(lastUsed);
                });
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        final PersistedToken persistedToken = tokenRepository.getOne(seriesId);
        return new PersistentRememberMeToken(persistedToken.getUsername(), persistedToken.getSeries(),
                persistedToken.getTokenValue(), persistedToken.getLastUsed());
    }

    @Override
    public void removeUserTokens(String username) {
        tokenRepository.deleteByUsername(username);
    }
}
