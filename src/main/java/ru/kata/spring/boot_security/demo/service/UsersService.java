package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UsersService {
    public List<User> findAll();
    public User findOne(int id);
    public boolean save(User user, int[] roles);
    public void update(int id, User updatedUser);
    public void delete(int id);
}
