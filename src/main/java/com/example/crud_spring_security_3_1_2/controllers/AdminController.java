package com.example.crud_spring_security_3_1_2.controllers;

import com.example.crud_spring_security_3_1_2.model.User;
import com.example.crud_spring_security_3_1_2.service.RoleService;
import com.example.crud_spring_security_3_1_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping("")
    public String listUsers(Model model){
        model.addAttribute("users", userService.getUsers());
        return "admin";
    }

    @GetMapping("/get/{id}")
    public String showUserInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user){
        return "new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user, @RequestParam("listRoles") ArrayList<Long> roles){
        userService.save(user, roleService.findRoles(roles));
        return "redirect:/admin";
    }


    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id){
        model.addAttribute("user", userService.getUser(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @RequestParam("listRoles") ArrayList<Long> roles){
        userService.update(user, roleService.findRoles(roles));
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
