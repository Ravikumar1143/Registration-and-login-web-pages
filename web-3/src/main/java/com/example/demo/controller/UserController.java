package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listUsers(Model model) { //display users in index.jsp
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "index";
    }

  

    @PostMapping("/user")
    public String saveUser(User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

   

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @PostMapping("/users/deleteAll")
    public String deleteAllUsers() {
        userService.deleteAllUsers();
        return "redirect:/users";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user) {
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(User user, Model model) {
        User loggedInUser = userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if (loggedInUser != null) {
            return "redirect:/users";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
}
