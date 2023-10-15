package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UsersService;

@Controller
public class UsersController {
    private final UsersService userService;
    @Autowired
    public UsersController(UsersService userService) {

        this.userService = userService;
    }
    @GetMapping("/user/{id}")
    public String showUser(@PathVariable("id") int id, Model model, Authentication authentication) {
        int currentUserId = ((User) authentication.getPrincipal()).getId();

        if (currentUserId != id) {
            return ("redirect:/user/" + currentUserId);
        }
        model.addAttribute("user", userService.findOne(id));
        return "users/im";
    }
}
