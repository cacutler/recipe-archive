package com.cacutler.recipearchive.controller;

import com.cacutler.recipearchive.dto.AuthRequest;
import com.cacutler.recipearchive.dto.AuthResponse;
import com.cacutler.recipearchive.entity.User;
import com.cacutler.recipearchive.repository.UserRepository;
import com.cacutler.recipearchive.security.CustomUserDetailsService;
import com.cacutler.recipearchive.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        AuthResponse response = new AuthResponse(jwt, user.getUsername(), user.getId());
        return ResponseEntity.ok(response);
    }
}