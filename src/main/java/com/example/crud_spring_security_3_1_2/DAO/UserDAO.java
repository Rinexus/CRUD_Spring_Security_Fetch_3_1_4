package com.example.crud_spring_security_3_1_2.DAO;

import com.example.crud_spring_security_3_1_2.model.Role;
import com.example.crud_spring_security_3_1_2.model.User;

import java.util.List;
import java.util.Set;

public interface UserDAO {
    List<User> getUsers();
    User getUser(Long id);
    User getUserByName(String username);
    void save(User user);
    void update(User updatedUser, Set<Role> roles);
    void delete(Long id);
}
