package com.app.cinema.service;

import com.app.cinema.helper.AuthorityType;
import com.app.cinema.helper.NotFoundInDB;
import com.app.cinema.Entity.User;
import com.app.cinema.repository.UserRepository;
import com.app.cinema.security.MyPasswordEncoder;
import com.app.cinema.service.interfaces.AuthorityService;
import com.app.cinema.service.interfaces.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MyPasswordEncoder passwordEncoder;
    private final AuthorityService authorityService;

    public UserServiceImpl(UserRepository userRepository, MyPasswordEncoder passwordEncoder, AuthorityService authorityService) {
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.authorityService=authorityService;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email) throws UsernameNotFoundException {
        Optional<User> optional=userRepository.findByEmail(email);
        if (optional.isPresent()) return optional.get();
        else throw new UsernameNotFoundException("User email '"+email+"' not exist");
    }

    @Override
    public void save(User user) {
        try{
            this.findByEmail(user.getEmail());
        } catch (UsernameNotFoundException usernameNotFoundException) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setAuthorities(authorityService.createOrGetAuthorities(new AuthorityType[]{AuthorityType.ROLE_USER}));
        userRepository.save(user);
        userRepository.flush();
    }

    //no usages
    @Override
    public User update(User user) {
        User userToUpdate = userRepository.findById(user.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userToUpdate.setVerified(user.getVerified());
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        return userRepository.save(userToUpdate);
    }

    public User getUser(String username) throws NotFoundInDB {
        return this.findByEmail(username);
    }


}
