package com.cwz.springboot.token_demo.jpa.repository;

import com.cwz.springboot.token_demo.jpa.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    @Select("select * from user where username = #{username}")
    public User findByUsername(@Param("username") String username);

}
