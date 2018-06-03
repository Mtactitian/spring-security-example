package com.alexb.repository;

import com.alexb.model.AuthorizedUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AuthorizedUser, String> {
}
