package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class UsersServiceImp implements UsersService {
    private final RolesRepository rolesRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder getPasswordEncoder;

    @Autowired
    public UsersServiceImp(RolesRepository rolesRepository, UsersRepository usersRepository, PasswordEncoder getPasswordEncoder) {
        this.rolesRepository = rolesRepository;
        this.usersRepository = usersRepository;
        this.getPasswordEncoder = getPasswordEncoder;
    }
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    public User findOne(int id) {
        Optional<User> user = usersRepository.findById(id);

        return user.orElse(null);
    }

    @Transactional
    public void save(User user) {
        user.setPassword(getPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Transactional
    public void update(int id, User updatedUser) {
        User existingUser = usersRepository.findById(id).orElse(null);

        if (existingUser != null) {
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setName(updatedUser.getName());
            existingUser.setLastname(updatedUser.getLastname());
            existingUser.setAge(updatedUser.getAge());
            existingUser.setPassword(getPasswordEncoder.encode(updatedUser.getPassword()));
            existingUser.setRoles(updatedUser.getRoles());

            usersRepository.save(existingUser);
        }
    }

    @Transactional
    public void delete(int id) {
        usersRepository.deleteById(id);
    }

}
