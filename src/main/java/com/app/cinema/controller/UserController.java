package com.app.cinema.controller;

import com.app.cinema.dto.UserDto;
import com.app.cinema.model.User;
import com.app.cinema.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper=modelMapper;
        this.userService=userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> user(@AuthenticationPrincipal UserDetails userDetails) {
//        HashMap<Object, Object> model = new HashMap<>();
//        model.put("username", userDetails.getUsername());
//        model.put("roles", userDetails.getAuthorities());
//        return ResponseEntity.ok(model);
        Optional<User> optionalUser=userService.findByEmail(userDetails.getUsername());
        if(optionalUser.isPresent()) {
            return ResponseEntity.ok(this.convertToDto(optionalUser.get()));
        }
        throw new UsernameNotFoundException("User not found");
    }

    private UserDto convertToDto(User user) {
            return this.modelMapper.map(user, UserDto.class);
    }


}
