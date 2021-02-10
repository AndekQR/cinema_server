package com.app.cinema.repository;

import com.app.cinema.Entity.JwtRefreshToken;
import com.app.cinema.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, String> {
    void deleteByUser(User user);
}
