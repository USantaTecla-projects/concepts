package com.example.backend.e2e.resources.user;


import com.example.backend.e2e.resources.user.dto.UserDTO;
import com.example.backend.e2e.resources.user.exception.model.UserAlreadyExistsException;
import com.example.backend.e2e.resources.user.exception.model.UserDTOBadRequestException;
import com.example.backend.e2e.resources.user.exception.model.UserNotFoundException;
import com.example.backend.e2e.resources.user.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        System.out.println(userDTO);
        final String fullNameFromDTO = userDTO
                .getFullNameOptional(userDTO.getFullName())
                .orElseThrow(() -> new UserDTOBadRequestException("Field fullName in User DTO is mandatory"));

        final String usernameFromDTO = userDTO
                .getUsernameOptional(userDTO.getUsername())
                .orElseThrow(() -> new UserDTOBadRequestException("Field username in User DTO is mandatory"));

        final String emailFromDTO = userDTO
                .getEmailOptional(userDTO.getEmail())
                .orElseThrow(() -> new UserDTOBadRequestException("Field email in User DTO is mandatory"));

        final String passwordFromDTO = userDTO
                .getPasswordOptional(userDTO.getPassword())
                .orElseThrow(() -> new UserDTOBadRequestException("Field password in User DTO is mandatory"));

        User userToSave = new User(
                fullNameFromDTO,
                usernameFromDTO,
                emailFromDTO,
                passwordEncoder.encode(passwordFromDTO),
                new LinkedList<>(List.of("STUDENT"))
        );
        try{
            return userRepository.save(userToSave);
        } catch (DataIntegrityViolationException dataIntegrityViolationException){
            throw new UserAlreadyExistsException("User with this email or username already exists");
        }
    }

    public User findOneByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("The user with username = " + username + " has not been found"));
    }

    public User findOneById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("The user with ID = " + userId + " has not been found"));
    }


    public Page<User> findAll(int page) {
        int pageSize = 5;
        return userRepository.findAll(PageRequest.of(page, pageSize));
    }

    public void updateOne(Long userId, UserDTO userDTO) {
        User user = findOneById(userId);
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
        User user = findOneById(userId);
        userRepository.delete(user);
    }
}
