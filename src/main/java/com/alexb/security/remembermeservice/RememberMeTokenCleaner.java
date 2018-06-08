package com.alexb.security.remembermeservice;

import com.alexb.repository.RememberMeTokenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class RememberMeTokenCleaner implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(RememberMeTokenCleaner.class);

    private final RememberMeTokenRepository rememberMeTokenRepository;

    @Override
    @Transactional
    public void run() {
        LocalDateTime ldt = LocalDateTime.now().minusMinutes(10);
        Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        int deleteTokenCount = rememberMeTokenRepository.removeByLastUsedBefore(date);

        if (deleteTokenCount > 0) {
            logger.info(String.format("Remember Me Tokens Cleared : %s Token Count : %d", LocalDateTime.now(), deleteTokenCount));
        }
    }
}