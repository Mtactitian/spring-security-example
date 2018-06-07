package com.alexb.repository;

import com.alexb.model.PersistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RememberMeTokenRepository extends JpaRepository<PersistedToken, String> {

    @Modifying
    @Query
    void deleteByUsername(String username);
}
