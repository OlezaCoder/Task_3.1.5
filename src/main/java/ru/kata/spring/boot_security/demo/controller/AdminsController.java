package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UsersService;
import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class AdminsController {

    private final UsersService userService;
    @Autowired
    public AdminsController(UsersService userService) {

        this.userService = userService;
    }

    @GetMapping()
    public String showUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("users", userService.findAll());
        return "admin/adminPage";
    }


    @GetMapping("/new")
    public String newUser(@AuthenticationPrincipal User authUser, @ModelAttribute("user") User user, Model model) {
        model.addAttribute("authUser", authUser);
        return "admin/addUser";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user, @RequestParam(value = "roles") int[] roles) {
        userService.save(user, roles);
        return "redirect:/admin";
    }


    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, @PathVariable("id") int id) {
        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
