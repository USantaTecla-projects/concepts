package com.example.backend.api.resources.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String username;

    private String password;

    public Optional<String> getUsernameOptional(final String username) {
        return getTextOptional(username);
    }

    public Optional<String> getPasswordOptional(final String password) {
        return getTextOptional(password);
    }

    private Optional<String> getTextOptional(final String text) {
        return Optional
                .ofNullable(text)
                .filter(t -> !t.isEmpty());
    }

}
