package com.example.backend.e2e.resources.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String fullName;

    private String username;

    private String email;

    private String password;

    public Optional<String> getFullNameOptional(final String fullName) {
        return getTextOptional(fullName);
    }

    public Optional<String> getUsernameOptional(final String username) {
        return getTextOptional(username);
    }

    public Optional<String> getEmailOptional(final String email) {
        return getTextOptional(email);
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
