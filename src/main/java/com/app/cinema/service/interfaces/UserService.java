package com.app.cinema.service.interfaces;

import com.app.cinema.helper.NotFoundInDB;
import com.app.cinema.Entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    User findById(Long id);
    User findByEmail(String email) throws UsernameNotFoundException;
    void save(User user);
    User update(User user);
    User getUser(String username) throws NotFoundInDB;
}
