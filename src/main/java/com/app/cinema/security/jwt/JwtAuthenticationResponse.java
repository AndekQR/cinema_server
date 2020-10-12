package com.app.cinema.security.jwt;

import com.app.cinema.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long expires;
    private User user;

    public JwtAuthenticationResponse(String token, String refreshToken, Long expires, User user) {
        this.token=token;
        this.refreshToken = refreshToken;
        this.expires=expires;
        this.user=user;
    }
}
