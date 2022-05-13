package com.example.myhome.repository;

import com.example.myhome.model.User;

import java.util.List;

public interface CustomizedUserRepository {

    List<User> findByUsernameCustom(String username);

    List<User> findByUsernameJdbc(String username);
}
