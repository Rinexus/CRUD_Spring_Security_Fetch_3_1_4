package com.example.crud_spring_security_3_1_2.service;

import com.example.crud_spring_security_3_1_2.model.Role;
import com.example.crud_spring_security_3_1_2.model.User;
import com.example.crud_spring_security_3_1_2.DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder PasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.PasswordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    @Override
    public User getUser(Long id) {
        return userDAO.getUser(id);
    }

    @Transactional
    @Override
    public void save(User user, Set<Role> roles) {
        user.setRoles(roles);
        user.setPassword(PasswordEncoder.encode(user.getPassword()));
        userDAO.save(user);
    }

    @Transactional
    @Override
    public void update(User user, Set<Role> roles) {
        user.setRoles(roles);
        if(userDAO.getUser(user.getId()).getPassword().equals(user.getPassword())){
            userDAO.update(user);
        } else {
            user.setPassword(PasswordEncoder.encode(user.getPassword()));
            userDAO.update(user);
        }
    }

    @Transactional
    @Override
    public void delete (Long id) {
        userDAO.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.getUserByName(username);
    }
}
