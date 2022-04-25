package com.auditManagement.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.auditManagement.authorization.entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);
}
