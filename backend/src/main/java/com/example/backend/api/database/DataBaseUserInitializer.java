package com.example.backend.api.database;

import com.example.backend.api.resources.user.UserRepository;
import com.example.backend.api.resources.user.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class DataBaseUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataBaseUserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        userRepository
                .save(new User(1L, "teacher", passwordEncoder.encode("1234"), List.of("TEACHER", "STUDENT")));
        userRepository
                .save(new User(2L, "student", passwordEncoder.encode("1234"), List.of("STUDENT")));
    }


}
