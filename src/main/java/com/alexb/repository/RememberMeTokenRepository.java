package com.alexb.repository;

import com.alexb.model.PersistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Date;

public interface RememberMeTokenRepository extends JpaRepository<PersistedToken, String> {

    void removeByUsername(String username);

    @Modifying(flushAutomatically = true)
    int removeByLastUsedBefore(Date date);
}
