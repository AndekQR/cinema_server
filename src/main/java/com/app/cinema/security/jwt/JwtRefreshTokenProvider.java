package com.app.cinema.security.jwt;

import com.app.cinema.model.User;
import com.app.cinema.repository.JwtRefreshTokenRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Component
@ConfigurationProperties(prefix="token")
public class JwtRefreshTokenProvider {

    private Integer refreshTokenValidityMs;
    private final JwtRefreshTokenRepository jwtRefreshTokenRepository;

    public JwtRefreshTokenProvider(JwtRefreshTokenRepository jwtRefreshTokenRepository) {
        this.jwtRefreshTokenRepository=jwtRefreshTokenRepository;
    }

    public Boolean isValid(String refreshToken){
        Optional<JwtRefreshToken> jwtRefreshToken = jwtRefreshTokenRepository.findById(refreshToken);
        if (jwtRefreshToken.isPresent()){
            Instant expirationDateTime=jwtRefreshToken.get().getExpirationDateTime();
            return Instant.now().isBefore(expirationDateTime);
        }
        return false;
    }

    public Optional<User> getUser(String refreshToken){
        Optional<JwtRefreshToken> jwtRefreshToken = jwtRefreshTokenRepository.findById(refreshToken);
        return jwtRefreshToken.map(JwtRefreshToken::getUser);
    }

    private String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    public void deleteOldToken(User user){
        this.jwtRefreshTokenRepository.deleteByUser(user);
    }

    private JwtRefreshToken saveRefreshToken(User user, String refreshToken) {

        JwtRefreshToken jwtRefreshToken = new JwtRefreshToken(refreshToken);
        jwtRefreshToken.setUser(user);

        Instant expirationDateTime = Instant.now().plusMillis(refreshTokenValidityMs);
        jwtRefreshToken.setExpirationDateTime(expirationDateTime);

        //TODO: transakcja?
        this.deleteOldToken(user);

        return jwtRefreshTokenRepository.save(jwtRefreshToken);
    }

    public JwtRefreshToken createRefreshToken(User user){
        String token = this.generateRefreshToken();
        return this.saveRefreshToken(user, token);
    }

    public void setRefreshTokenValidityMs(Integer refreshTokenValidityMs) {
        this.refreshTokenValidityMs=refreshTokenValidityMs;
    }
}
