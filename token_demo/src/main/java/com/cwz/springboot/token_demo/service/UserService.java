package com.cwz.springboot.token_demo.service;

import com.cwz.springboot.token_demo.jpa.entity.User;

public interface UserService {

    User findUserByUsername(String username);

    User addUser(User user);
}
