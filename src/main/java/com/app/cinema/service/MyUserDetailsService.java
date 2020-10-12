package com.app.cinema.service;

import com.app.cinema.service.interfaces.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public MyUserDetailsService(UserService userService) {
        this.userService=userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));
    }
}
