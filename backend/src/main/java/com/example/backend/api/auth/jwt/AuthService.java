package com.example.backend.api.auth.jwt;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.backend.api.auth.jwt.components.JwtCookieManager;
import com.example.backend.api.auth.jwt.components.JwtTokenProvider;
import com.example.backend.api.auth.jwt.dto.AuthResponse;
import com.example.backend.api.auth.jwt.dto.LoginRequest;
import com.example.backend.api.auth.jwt.dto.Token;
import com.example.backend.api.auth.jwt.util.SecurityCipher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtTokenProvider jwtTokenProvider;

    private final JwtCookieManager cookieUtil;

    public AuthService(AuthenticationManager authenticationManager,
                       UserDetailsService userDetailsService,
                       JwtTokenProvider jwtTokenProvider,
                       JwtCookieManager cookieUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.cookieUtil = cookieUtil;

    }

    public ResponseEntity<AuthResponse> login(LoginRequest loginRequest, String encryptedAccessToken, String
            encryptedRefreshToken) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = SecurityCipher.decrypt(encryptedAccessToken);
        String refreshToken = SecurityCipher.decrypt(encryptedRefreshToken);

        String username = loginRequest.getUsername();
        UserDetails user = userDetailsService.loadUserByUsername(username);

        boolean accessTokenValid = jwtTokenProvider.validateToken(accessToken);
        boolean refreshTokenValid = jwtTokenProvider.validateToken(refreshToken);

        HttpHeaders responseHeaders = new HttpHeaders();
        Token newAccessToken;
        Token newRefreshToken;
        if (!accessTokenValid && !refreshTokenValid) {
            newAccessToken = jwtTokenProvider.generateToken(user);
            newRefreshToken = jwtTokenProvider.generateRefreshToken(user);
            addAccessTokenCookie(responseHeaders, newAccessToken);
            addRefreshTokenCookie(responseHeaders, newRefreshToken);
        }

        if (!accessTokenValid && refreshTokenValid) {
            newAccessToken = jwtTokenProvider.generateToken(user);
            addAccessTokenCookie(responseHeaders, newAccessToken);
        }

        if (accessTokenValid && refreshTokenValid) {
            newAccessToken = jwtTokenProvider.generateToken(user);
            newRefreshToken = jwtTokenProvider.generateRefreshToken(user);
            addAccessTokenCookie(responseHeaders, newAccessToken);
            addRefreshTokenCookie(responseHeaders, newRefreshToken);
        }

        AuthResponse loginResponse = new AuthResponse(AuthResponse.Status.SUCCESS,
                "Auth successful. Tokens are created in cookie.");


        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
    }

    public String logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session;
        SecurityContextHolder.clearContext();
        session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                cookie.setMaxAge(0);
                cookie.setValue("");
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }

        return "logout successfully";
    }

    private void addAccessTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE,
                cookieUtil.createAccessTokenCookie(token.getTokenValue(), token.getDuration()).toString());
    }

    private void addRefreshTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE,
                cookieUtil.createRefreshTokenCookie(token.getTokenValue(), token.getDuration()).toString());
    }
}
