package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UsersService {
    List<User> findAll();
    User findOne(int id);
    boolean save(User user, int[] roles);
    void update(int id, User updatedUser);
    void delete(int id);
}
