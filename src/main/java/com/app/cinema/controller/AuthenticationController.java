package com.app.cinema.controller;

import com.app.cinema.helper.AuthorityType;
import com.app.cinema.helper.UserAlreadyInDatabaseException;
import com.app.cinema.security.jwt.JwtRefreshToken;
import com.app.cinema.model.User;
import com.app.cinema.security.AuthenticationRequest;
import com.app.cinema.security.jwt.*;
import com.app.cinema.service.interfaces.AuthorityService;
import com.app.cinema.service.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final AuthorityService authorityService;
    private final JwtRefreshTokenProvider jwtRefreshTokenProvider;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, AuthorityService authorityService, JwtRefreshTokenProvider jwtRefreshTokenProvider) {
        this.authenticationManager=authenticationManager;
        this.jwtTokenProvider=jwtTokenProvider;
        this.userService=userService;
        this.authorityService=authorityService;
        this.jwtRefreshTokenProvider=jwtRefreshTokenProvider;
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtAuthenticationResponse> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws InvalidJwtAuthenticationException {
        if (jwtRefreshTokenProvider.isValid(refreshTokenRequest.getRefreshToken())){
            Optional<User> user = jwtRefreshTokenProvider.getUser(refreshTokenRequest.getRefreshToken());
            if (user.isPresent()){
                String accessToken = jwtTokenProvider.createToken(user.get().getUsername(), user.get().getAuthoritiesList());
                return ResponseEntity.ok(new JwtAuthenticationResponse(accessToken, refreshTokenRequest.getRefreshToken(), jwtTokenProvider.getExpirationDate(accessToken).getTime(), user.get()));
            }
        }
        throw new InvalidJwtAuthenticationException("Invalid Refresh Token");
    }


    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody AuthenticationRequest data) {
        String username = data.getUsername();
        User user = userService.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username "+username+" not found"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
        String token = jwtTokenProvider.createToken(username, user.getAuthoritiesList());
        JwtRefreshToken refreshToken = jwtRefreshTokenProvider.createRefreshToken(user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(token);
        jwtAuthenticationResponse.setExpires(jwtTokenProvider.getExpirationDate(token).getTime());
        jwtAuthenticationResponse.setRefreshToken(refreshToken.getToken());
        jwtAuthenticationResponse.setUser(user);

        return ResponseEntity.ok(jwtAuthenticationResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<Object, Object>> register(@RequestBody User user) throws UserAlreadyInDatabaseException {

        if (userService.findByEmail(user.getEmail()).orElse(null) == null) {
            user.setAuthorities(authorityService.createOrGetAuthorities(new AuthorityType[]{AuthorityType.ROLE_USER}));
            user.setVerified(true);
            userService.save(user);
        } else {
            throw new UserAlreadyInDatabaseException("User already in database");
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/logout")
    public ResponseEntity.BodyBuilder logout(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> user = userService.findByEmail(userDetails.getUsername());
        if (user.isPresent()){
            jwtRefreshTokenProvider.deleteOldToken(user.get());
            return ResponseEntity.ok();
        }
        throw new UsernameNotFoundException("User not found");
    }
}
