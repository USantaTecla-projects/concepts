package com.example.backend.database;

import com.example.backend.api.user.UserRepository;
import com.example.backend.api.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class DataBaseLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataBaseLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository
                .save(new User(1L,"teacher", passwordEncoder.encode("1234"), List.of("TEACHER","STUDENT")));
        userRepository
                .save(new User(2L,"student", passwordEncoder.encode("1234"), List.of("STUDENT")));
    }



}
