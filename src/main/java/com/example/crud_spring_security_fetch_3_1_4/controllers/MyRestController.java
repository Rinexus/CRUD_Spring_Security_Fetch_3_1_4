package com.example.crud_spring_security_fetch_3_1_4.controllers;

import com.example.crud_spring_security_fetch_3_1_4.model.User;
import com.example.crud_spring_security_fetch_3_1_4.service.RoleService;
import com.example.crud_spring_security_fetch_3_1_4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class MyRestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public MyRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public List<User> listUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User showUserInfo(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PostMapping
    public List<User> createUser(@RequestBody User userToBeCreated){
        userService.save(userToBeCreated, userToBeCreated.getRoles());
        return userService.getUsers();
    }

    @PutMapping
    public List<User> updateUser(@RequestBody User userToBeUpdated) {
        userService.update(userToBeUpdated, userToBeUpdated.getRoles());
        return userService.getUsers();
    }

    @DeleteMapping("/{id}")
    public List<User> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return userService.getUsers();
    }
}
