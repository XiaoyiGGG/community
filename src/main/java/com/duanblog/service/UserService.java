package com.duanblog.service;

import com.duanblog.model.User;

import java.util.List;

public interface UserService {

    void createOrUpdate(User user);

    List<User> findUserByToken(String token);

    void insert(User user);


}
