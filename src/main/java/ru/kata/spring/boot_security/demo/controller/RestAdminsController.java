package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.exceptions.ExceptionInfo;
import ru.kata.spring.boot_security.demo.exceptions.UsernameExistException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UsersService;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;


@RestController
@RequestMapping("/api")
public class RestAdminsController {

    private final UsersService userService;
    @Autowired
    public RestAdminsController(UsersService userService) {

        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }


    @PostMapping("/users")
    public ResponseEntity<ExceptionInfo> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new ExceptionInfo(error), HttpStatus.BAD_REQUEST);
        }
        try {
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UsernameExistException e) {
            throw new UsernameExistException("User with this username exist");
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ExceptionInfo> updateUser(@Valid @RequestBody User user, @PathVariable("id") int id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new ExceptionInfo(error), HttpStatus.BAD_REQUEST);
        }
        try {
            userService.update(id, user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UsernameExistException e) {
            throw new UsernameExistException("User with this username exist");
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ExceptionInfo> deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return new ResponseEntity<>(new ExceptionInfo("User deleted"), HttpStatus.OK);
    }

    private String getErrorsFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
    }
}
