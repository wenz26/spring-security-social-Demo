package com.cwz.springboot.sercuritydemo.demo.repository;

import com.cwz.springboot.sercuritydemo.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
