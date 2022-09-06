package com.example.backend.api.resources.user;


import com.example.backend.api.resources.user.dto.UserDTO;
import com.example.backend.api.resources.user.exception.model.UserAlreadyExistsException;
import com.example.backend.api.resources.user.exception.model.UserDTOBadRequestException;
import com.example.backend.api.resources.user.exception.model.UserNotFoundException;
import com.example.backend.api.resources.user.model.User;
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

    /**
     * Create a new user by a given DTO (if it is correct).
     *
     * @param userDTO The data object to create the new user.
     * @return The created user.
     * @author Cristian
     */
    public User create(final UserDTO userDTO) {

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

    /**
     * Find a user in the database. If the user does not exist, it throws an exception.
     *
     * @param username The username to look for.
     * @return The user that match the username.
     * @author Cristian
     */
    public User findOneByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("The user with username = " + username + " has not been found"));
    }

    /**
     * Find a user in the database. If the user does not exist, it throws an exception.
     *
     * @param userId The user ID to look for.
     * @return The user that match the ID.
     * @author Cristian
     */
    public User findOneById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("The user with ID = " + userId + " has not been found"));
    }


    /**
     * Find a page of users in the database by the given page number.
     *
     * @param page The number of the page to look for.
     * @return The page with the users.
     * @author Cristian
     */
    public Page<User> findAll(int page) {
        int pageSize = 5;
        return userRepository.findAll(PageRequest.of(page, pageSize));
    }

    /**
     * Update one user by the given ID, with the given DTO.
     *
     * @param userId  The user ID to look for.
     * @param userDTO The data object to update in the user.
     * @author Cristian
     */
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

    /**
     * Remove one user by the given ID.
     *
     * @param userId The user ID to look for.
     * @author Cristian
     */
    public void removeOne(Long userId) {
        User user = findOneById(userId);
        userRepository.delete(user);
    }
}
