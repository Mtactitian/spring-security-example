package com.alexb.configuration;

import com.alexb.security.remembermeservice.RememberMeTokenCleaner;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulingConfig {

    private final RememberMeTokenCleaner rememberMeTokenCleaner;

    @Scheduled(fixedRateString = "PT10M")
    public void clearRememberMeTokens() {
        rememberMeTokenCleaner.run();
    }
}