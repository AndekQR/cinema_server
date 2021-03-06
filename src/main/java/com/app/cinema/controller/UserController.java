package com.app.cinema.controller;

import com.app.cinema.dto.UserDto;
import com.app.cinema.Entity.User;
import com.app.cinema.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {


    private final UserService userService;
    private final Mapper mapper;


    @GetMapping("/me")
    public ResponseEntity<UserDto> user(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User user=userService.findByEmail(userDetails.getUsername());
            return ResponseEntity.ok(mapper.mapObject(user, UserDto.class));
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("User not found");

        }
    }

}
