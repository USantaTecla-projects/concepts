package com.example.backend.api.resources.user;


import com.example.backend.api.resources.user.dto.UserDTO;
import com.example.backend.api.resources.user.exception.model.UserDTOBadRequestException;
import com.example.backend.api.resources.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User create(final UserDTO userDTO) {
        final String usernameFromDTO = userDTO
                .getUsernameOptional(userDTO.getUsername())
                .orElseThrow(() -> new UserDTOBadRequestException("Field username in User DTO is mandatory"));

        final String passwordFromDTO = userDTO
                .getPasswordOptional(userDTO.getPassword())
                .orElseThrow(() -> new UserDTOBadRequestException("Field username in User DTO is mandatory"));

        return userRepository.save(new User(usernameFromDTO, passwordEncoder.encode(passwordFromDTO), new LinkedList<>(List.of("STUDENT"))));
    }

    public User findOne(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("The user with id = " + userId + " has not been found"));
    }

    public Page<User> findAll(int page) {
        int pageSize = 5;
        return userRepository.findAll(PageRequest.of(page, pageSize));
    }

    public void updateOne(Long id, UserDTO userDTO) {
        User user = findOne(id);
        String usernameFromDTO = userDTO
                .getUsernameOptional(userDTO.getUsername())
                .orElse(user.getUsername());

        String passwordFromDTO = userDTO
                .getPasswordOptional(userDTO.getPassword())
                .orElse(user.getPassword());

        user.setUsername(usernameFromDTO);
        user.setPassword(passwordFromDTO);

        userRepository.save(user);
    }

    public void removeOne(Long userId) {
        User user = findOne(userId);
        userRepository.delete(user);
    }
}
