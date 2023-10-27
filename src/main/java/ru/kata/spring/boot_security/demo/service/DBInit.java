package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
@Component
public class DBInit {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder getPasswordEncoder;
    public DBInit(UserRepository usersRepository, RoleRepository rolesRepository, PasswordEncoder getPasswordEncoder) {
        this.userRepository = usersRepository;
        this.roleRepository = rolesRepository;
        this.getPasswordEncoder = getPasswordEncoder;
    }
    @PostConstruct
    private void initializeUsers() {
        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");

        roleRepository.save(adminRole);
        roleRepository.save(userRole);

        User adminUser = new User("admin", "admin", (byte) 45, "admin",
                getPasswordEncoder.encode("admin"), new HashSet<>(Arrays.asList(adminRole, userRole)));
        User user1 = new User("Oleg", "Kopysov", (byte) 22, "oleza",
                getPasswordEncoder.encode("backendtop"), Collections.singleton(userRole));

        userRepository.save(adminUser);
        userRepository.save(user1);
    }
}
