package com.alexb.service;

import com.alexb.model.AuthorizedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LoginListener {

    private final Logger logger = LoggerFactory.getLogger(LoginListener.class);

    @EventListener(classes = AuthorizedUser.class)
    public void saveLoginEvent(AuthorizedUser authorizedUser) {
        logger.info(authorizedUser.getUsername() + " is logged.");
    }

}