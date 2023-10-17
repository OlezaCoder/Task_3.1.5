package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class DbInit {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder getPasswordEncoder;

    public DbInit(UsersRepository usersRepository, RolesRepository rolesRepository, PasswordEncoder getPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.getPasswordEncoder = getPasswordEncoder;
    }
    @PostConstruct
    private void initializeUsers() {
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");

        rolesRepository.save(adminRole);
        rolesRepository.save(userRole);

        User adminUser = new User("admin", "admin", "admin", 45,
                getPasswordEncoder.encode("admin"), new HashSet<>(Arrays.asList(adminRole, userRole)));
        User user1 = new User("oleza", "Oleg", "Kopysov", 22,
                getPasswordEncoder.encode("backendtop"), Collections.singleton(userRole));

        usersRepository.save(adminUser);
        usersRepository.save(user1);
    }
}
