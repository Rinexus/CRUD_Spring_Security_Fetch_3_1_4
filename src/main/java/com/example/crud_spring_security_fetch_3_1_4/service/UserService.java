package com.example.crud_spring_security_fetch_3_1_4.service;

import com.example.crud_spring_security_fetch_3_1_4.model.Role;
import com.example.crud_spring_security_fetch_3_1_4.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    List<User> getUsers();
    User getUser(Long id);
    void save(User user, Set<Role> roles);
    void update(User user, Set<Role> roles);
    void delete(Long id);
}
