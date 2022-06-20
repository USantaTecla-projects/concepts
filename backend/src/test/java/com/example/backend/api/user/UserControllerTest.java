package com.example.backend.api.user;


import com.example.backend.api.resources.auth.configuration.AuthConfiguration;
import com.example.backend.api.resources.auth.jwt.components.JwtRequestFilter;
import com.example.backend.api.resources.auth.jwt.components.JwtTokenProvider;
import com.example.backend.api.resources.auth.util.UserDetailsFinder;
import com.example.backend.api.resources.user.UserController;
import com.example.backend.api.resources.user.UserRepository;
import com.example.backend.api.resources.user.UserService;
import com.example.backend.api.resources.user.dto.UserDTO;
import com.example.backend.api.resources.user.exception.model.UserDTOBadRequestException;
import com.example.backend.api.resources.user.exception.model.UserNotFoundException;
import com.example.backend.api.resources.user.model.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static com.example.backend.util.MapObjectToJson.mapObjectToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({
        AuthConfiguration.class,
        UserDetailsFinder.class,
        JwtRequestFilter.class,
        JwtTokenProvider.class,
})
public class UserControllerTest {

    public final String BASE_URL = "/users/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    UserRepository userRepository;


    @Nested
    @DisplayName("POST")
    class UserPost {
        @Test
        @DisplayName("(Create) Should get 201 if the DTO is correct")
        void createWithCorrectDTO() throws Exception {
            final UserDTO userDTO = new UserDTO("Cristian", "1234");
            final User user = new User(1L, userDTO.getUsername(), userDTO.getPassword(), new LinkedList<>(List.of("STUDENT")));

            when(userService.create(userDTO))
                    .thenReturn(user);

            final String userJsonDTO = mapObjectToJson(userDTO);

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(userJsonDTO))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("id").exists())
                    .andExpect(jsonPath("username", Matchers.is(user.getUsername())))
                    .andExpect(jsonPath("password", Matchers.is(user.getPassword())));
        }

        @Test
        @DisplayName("Create) Should get 400 if the DTO is malformed")
        void createWithWrongDTO() throws Exception {
            final UserDTO wrongConceptDTO = new UserDTO();

            when(userService.create(any(UserDTO.class)))
                    .thenThrow(new UserDTOBadRequestException("Field username in User DTO is mandatory"));

            final String conceptJsonDTO = mapObjectToJson(wrongConceptDTO);

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(conceptJsonDTO))
                    .andExpect(status().isBadRequest());
        }

        // TODO: Make a test to check that the username is unique
    }

    @Nested
    @DisplayName("GET")
    class UserGet {
        @Test
        @DisplayName("(FindOne) Should get 200 if the user exists")
        void findOneWhenExists() throws Exception {
            final User user = new User(1L, "Cristian", "1234", new LinkedList<>(List.of("STUDENT")));

            when(userService.findOne(user.getId()))
                    .thenReturn(user);

            mockMvc.perform(get(BASE_URL + user.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").exists())
                    .andExpect(jsonPath("username", Matchers.is(user.getUsername())));
        }

        @Test
        @DisplayName("(FindOne) Should get 404 if the given id does not match in the database")
        void findOneWhenNotExists() throws Exception {
            final long wrongUserId = 99L;

            when(userService.findOne(wrongUserId))
                    .thenThrow(new UserNotFoundException("The user with id = " + wrongUserId + " has not been found"));

            mockMvc.perform(get(BASE_URL + wrongUserId))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("(FindAll) Should get 200 and some content if there are users")
        void findAllWhenDataExists() throws Exception {
            final User user1 = new User(1L, "Cristian", "1234", new LinkedList<>(List.of("STUDENT")));
            final User user2 = new User(2L, "Marcos", "5678", new LinkedList<>(List.of("STUDENT")));
            final Page<User> userPage0 = new PageImpl<>(List.of(user1, user2));
            final Page<User> userPage1 = new PageImpl<>(List.of());

            when(userService.findAll(0))
                    .thenReturn(userPage0);

            when(userService.findAll(1))
                    .thenReturn(userPage1);

            mockMvc.perform(get(BASE_URL + "?page=" + 0))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("content").isArray())
                    .andExpect(jsonPath("totalElements", Matchers.is(2)))
                    .andExpect(jsonPath("content.size()", Matchers.is(2)));

            mockMvc.perform(get(BASE_URL + "?page=" + 1))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("content").isArray())
                    .andExpect(jsonPath("totalElements", Matchers.is(0)))
                    .andExpect(jsonPath("content.size()", Matchers.is(0)));
        }
    }

    @Nested
    @DisplayName("PUT")
    class UserPut {
        @Test
        @DisplayName("(UpdateOne) Should get 204 if the user is updated")
        void updateWhenExists() throws Exception {
            final UserDTO userDTO = new UserDTO("Cristian", "1234");
            final User user = new User(1L, userDTO.getUsername(), userDTO.getPassword(), new LinkedList<>(List.of("STUDENT")));

            String userJsonDTO = mapObjectToJson(userDTO);

            mockMvc.perform(put(BASE_URL + user.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(userJsonDTO))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("(UpdateOne) Should get 404 if the Answer is not in the database")
        void updateWhenNotExists() throws Exception {
            final UserDTO userDTO = new UserDTO("Cristian", "1234");
            final long wrongAnswerId = 99L;


            doThrow(new UserNotFoundException("The user with id = " + wrongAnswerId + " has not been found"))
                    .when(userService).updateOne(wrongAnswerId, userDTO);

            String userJsonDTO = mapObjectToJson(userDTO);

            mockMvc.perform(put(BASE_URL + wrongAnswerId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(userJsonDTO))
                    .andExpect(status().isNotFound());
        }

    }

    @Nested
    @DisplayName("DELETE")
    class UserDelete {
        @Test
        @DisplayName("(RemoveOne) Should get 204 if the answer is in the concept answers list")
        void deleteWhenExists() throws Exception {
            final User user = new User(1L, "Cristian", "1234", new LinkedList<>(List.of("STUDENT")));


            mockMvc.perform(delete(BASE_URL + user.getId()))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("(RemoveOne) Should get 404 if the answer is not in the database")
        void deleteWhenNotExists() throws Exception {
            final long wrongAnswerId = 99L;

            doThrow(new UserNotFoundException("The user with id = " + wrongAnswerId + " has not been found"))
                    .when(userService).removeOne(wrongAnswerId);

            mockMvc.perform(delete(BASE_URL + wrongAnswerId))
                    .andExpect(status().isNotFound());
        }
    }

}
