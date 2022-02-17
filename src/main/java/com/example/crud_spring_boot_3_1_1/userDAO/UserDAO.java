package com.example.crud_spring_boot_3_1_1.userDAO;

import com.example.crud_spring_boot_3_1_1.model.User;

import java.util.List;

public interface UserDAO {
    List<User> getUsers();
    User getUser(int id);
    void save(User user);
    void update(User updatedUser);
    void delete(Integer id);
}
