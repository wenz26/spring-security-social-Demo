package com.cwz.springboot.token_demo.service.Impl;

import com.cwz.springboot.token_demo.jpa.entity.User;
import com.cwz.springboot.token_demo.jpa.repository.UserRepository;
import com.cwz.springboot.token_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findUserByUsername(String username) {

        User user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public User addUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return user;
    }
}
