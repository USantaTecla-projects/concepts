package com.example.backend.api.auth.configuration;

import com.example.backend.api.auth.jwt.components.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    public AuthConfiguration(

            UserDetailsService userDetailsService,
            JwtRequestFilter jwtRequestFilter
    ) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Private endpoints
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/concepts/**").hasRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/concepts/**").hasRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/concepts/**").hasRole("TEACHER");

        // Other endpoints are public
        http.authorizeRequests().anyRequest().permitAll();

        // Disable CSRF
        http.csrf().disable();

        // Avoid creating session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Exception handling
        http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        // Disable form login authentication
        http.formLogin().disable();

        // JWT filter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // Enable CORS filter
        http.cors();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
