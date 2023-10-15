package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder getPasswordEncoder;

    @Autowired
    public UsersServiceImp(UsersRepository usersRepository, RolesRepository rolesRepository, PasswordEncoder getPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
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
    public boolean save(User user) {
        Optional<User> userFromBD = usersRepository.findByUsername(user.getUsername());

        if (userFromBD.isPresent()) {
            return false;
        }


        user.setRoles(Collections.singleton(new Role(1)));
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

            usersRepository.save(existingUser);
        }
    }

    @Transactional
    public void delete(int id) {
        usersRepository.deleteById(id);
    }

}
