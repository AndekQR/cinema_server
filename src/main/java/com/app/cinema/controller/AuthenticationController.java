package com.app.cinema.controller;

import com.app.cinema.dto.UserDto;
import com.app.cinema.helper.AuthorityType;
import com.app.cinema.helper.UserAlreadyInDatabaseException;
import com.app.cinema.Entity.JwtRefreshToken;
import com.app.cinema.Entity.User;
import com.app.cinema.security.AuthenticationRequest;
import com.app.cinema.security.jwt.*;
import com.app.cinema.service.interfaces.AuthorityService;
import com.app.cinema.service.interfaces.UserService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final AuthorityService authorityService;
    private final JwtRefreshTokenProvider jwtRefreshTokenProvider;
    private final Mapper mapper;


    @PostMapping("/refreshToken")
    public ResponseEntity<JwtAuthenticationResponse> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws InvalidJwtAuthenticationException {
        if (jwtRefreshTokenProvider.isValid(refreshTokenRequest.getRefreshToken())){
            Optional<User> user = jwtRefreshTokenProvider.getUser(refreshTokenRequest.getRefreshToken());
            if (user.isPresent()){
                String accessToken = jwtTokenProvider.createToken(user.get().getUsername(), user.get().getAuthoritiesList());
                return ResponseEntity.ok(new JwtAuthenticationResponse(accessToken,
                        refreshTokenRequest.getRefreshToken(),
                        jwtTokenProvider.getExpirationDate(accessToken).getTime(),
                        mapper.mapObject(user.get(), UserDto.class)));
            }
        }
        throw new InvalidJwtAuthenticationException("Invalid Refresh Token");
    }


    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody AuthenticationRequest data) throws UsernameNotFoundException {
        String username = data.getUsername();
        User user=userService.findByEmail(username);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
        String token = jwtTokenProvider.createToken(username, user.getAuthoritiesList());
        JwtRefreshToken refreshToken = jwtRefreshTokenProvider.createRefreshToken(user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(token);
        jwtAuthenticationResponse.setExpires(jwtTokenProvider.getExpirationDate(token).getTime());
        jwtAuthenticationResponse.setRefreshToken(refreshToken.getToken());
        jwtAuthenticationResponse.setUser(mapper.mapObject(user, UserDto.class));

        return ResponseEntity.ok(jwtAuthenticationResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<Object, Object>> register(@RequestBody User user) throws UserAlreadyInDatabaseException {

        try {
            userService.findByEmail(user.getEmail());
        } catch (UsernameNotFoundException e) {
            user.setAuthorities(authorityService.createOrGetAuthorities(new AuthorityType[]{AuthorityType.ROLE_USER}));
            user.setVerified(true);
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        throw new UserAlreadyInDatabaseException("User already in database");
    }

    @GetMapping("/logout")
    public ResponseEntity.BodyBuilder logout(@AuthenticationPrincipal UserDetails userDetails) throws UsernameNotFoundException {
        User user=userService.findByEmail(userDetails.getUsername());
        jwtRefreshTokenProvider.deleteOldToken(user);
        return ResponseEntity.ok();
    }
}
