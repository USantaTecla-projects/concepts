package com.example.backend.api.resources.auth.util;

import com.example.backend.api.resources.user.UserRepository;
import com.example.backend.api.resources.user.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsFinder implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsFinder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("The user with username = " + username + " has not been found."));

        List<GrantedAuthority> grantedAuthorityList = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),grantedAuthorityList);

    }
}
