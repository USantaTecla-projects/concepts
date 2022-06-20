package com.example.backend.api.user;

import com.example.backend.api.resources.user.UserRepository;
import com.example.backend.api.resources.user.UserService;
import com.example.backend.api.resources.user.dto.UserDTO;
import com.example.backend.api.resources.user.exception.model.UserDTOBadRequestException;
import com.example.backend.api.resources.user.exception.model.UserNotFoundException;
import com.example.backend.api.resources.user.model.User;
import com.example.backend.config.TestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Import({TestConfig.class})
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;


    @Nested
    @DisplayName("POST")
    class UserPost {
        @Test
        @DisplayName("(Create) Should create a user with a correct DTO")
        void createWithCorrectDTO() {
            final UserDTO userDTO = new UserDTO("Cristian", "1234");
            final User user = new User(1L, userDTO.getUsername(), userDTO.getPassword(), new LinkedList<>(List.of("STUDENT")));

            when(userRepository.save(any(User.class)))
                    .thenReturn(user);

            User createdUser = userService.create(userDTO);

            assertEquals(user, createdUser);
        }

        @Test
        @DisplayName("(Create) Should not create a user with an incorrect DTO")
        void createWithWrongDTO() {
            final UserDTO wrongUserDTO = new UserDTO();

            assertThrows(UserDTOBadRequestException.class, () -> userService.create(wrongUserDTO));
        }
    }

    @Nested
    @DisplayName("GET")
    class UserGet {
        @Test
        @DisplayName("(FindOne) Should find one user with the given id")
        void findOneThatExist() {
            final User user = new User(1L, "Cristian", "1234", new LinkedList<>(List.of("STUDENT")));

            when(userRepository.findById(user.getId()))
                    .thenReturn(Optional.of(user));

            final User foundUser = userService.findOne(user.getId());

            assertEquals(user, foundUser);
        }

        @Test
        @DisplayName("(FindOne) Should throw an Exception with the given id")
        void findOneThatNotExist() {
            assertThrows(UserNotFoundException.class, () -> userService.findOne(2L));
        }

        @Test
        @DisplayName("(FindAll) Should find a page with 2 users")
        void findAllWhenDataExists() {
            final User user1 = new User(1L, "Cristian", "1234", new LinkedList<>(List.of("STUDENT")));
            final User user2 = new User(2L, "Marcos", "5678", new LinkedList<>(List.of("STUDENT")));
            final Page<User> userPage = new PageImpl<>(List.of(user1, user2));
            final int pageNumber = 0;
            final int pageSize = 5;

            when(userRepository.findAll(PageRequest.of(pageNumber, pageSize)))
                    .thenReturn(userPage);

            Page<User> foundPage = userService.findAll(pageNumber);
            assertEquals(foundPage.getNumberOfElements(), 2);
        }

        @Test
        @DisplayName("(FindAll) Should find an empty Page")
        void findAllWhenDataNotExists() {
            final int wrongPageNumber = 99;
            final int pageSize = 5;

            when(userRepository.findAll(PageRequest.of(wrongPageNumber, pageSize)))
                    .thenReturn(new PageImpl<>(Collections.emptyList()));

            Page<User> foundPage = userService.findAll(99);
            assertEquals(foundPage.getNumberOfElements(), 0);
        }
    }

    @Nested
    @DisplayName("PUT")
    class ConceptPut {
        @Test
        @DisplayName("(Update) Should update a Concept if exists")
        void updateWhenExists() {
            final UserDTO userDTO = new UserDTO("Cristian", "1234");
            final User user = new User(1L, userDTO.getUsername(), userDTO.getPassword(), new LinkedList<>(List.of("STUDENT")));


            when(userRepository.findById(user.getId()))
                    .thenReturn(Optional.of(user));

            userService.updateOne(user.getId(), userDTO);
        }

        @Test
        @DisplayName("(Update) Should throw an exception if the Concept is not found")
        void updateWhenNotExists() {
            final UserDTO userDTO = new UserDTO("Cristian", "1234");
            final long wrongUserId = 99L;

            when(userRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> userService.updateOne(wrongUserId, userDTO));
        }
    }

    @Nested
    @DisplayName("DELETE")
    class ConceptDelete {
        @Test
        @DisplayName("(Delete) Should delete a Concept if exists")
        void deleteWhenExists() {
            final User user = new User(1L, "Cristian", "1234", new LinkedList<>(List.of("STUDENT")));

            when(userRepository.findById(user.getId()))
                    .thenReturn(Optional.of(user));

            userService.removeOne(user.getId());
        }

        @Test
        @DisplayName("(Delete) Should throw an exception if the Concept is not found")
        void deleteWhenNotExists() {
            final long wrongUserId = 99L;

            when(userRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> userService.removeOne(wrongUserId));
        }
    }

}
