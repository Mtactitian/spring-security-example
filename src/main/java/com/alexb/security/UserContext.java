package com.alexb.security;

import com.alexb.model.AuthorizedUser;

public interface UserContext {
    void setCurrentUser(AuthorizedUser authorizedUser);
    void deleteUser(String name);
}
