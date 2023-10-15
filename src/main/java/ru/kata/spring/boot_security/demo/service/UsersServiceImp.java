package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class UsersServiceImp implements UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder getPasswordEncoder;

    @Autowired
    public UsersServiceImp(UsersRepository usersRepository, PasswordEncoder getPasswordEncoder) {
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
    public boolean save(User user, int[] roles) {
        Optional<User> userFromBD = usersRepository.findByUsername(user.getUsername());

        if (userFromBD.isPresent()) {
            return false;
        }

        Set<Role> userRoles = new HashSet<>();
        for (int roleId : roles) {
            userRoles.add(new Role(roleId));
        }

        user.setRoles(userRoles);
        user.setPassword(getPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        return true;
    }

    @Transactional
    public void update(int id, User updatedUser) {
        User existingUser = usersRepository.findById(id).orElse(null);

        if (existingUser != null) {
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setName(updatedUser.getName());
            existingUser.setLastname(updatedUser.getLastname());
            existingUser.setAge(updatedUser.getAge());
            existingUser.setRoles(updatedUser.getRoles());

            usersRepository.save(existingUser);
        }
    }

    @Transactional
    public void delete(int id) {
        usersRepository.deleteById(id);
    }

}
