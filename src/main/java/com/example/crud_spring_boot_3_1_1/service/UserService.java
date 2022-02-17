package com.example.crud_spring_boot_3_1_1.service;

import com.example.crud_spring_boot_3_1_1.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUser(int id);
    void save(User user);
    void update(User user);
    void delete(Integer id);
}
