package com.example.connect.auth.application;

import com.example.connect.user.domain.User;
import com.example.connect.user.domain.UserRepository;
import com.example.connect.common.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwt; // <-- 스프링이 주입

    @Transactional
    public User signup(String email, String username, String rawPassword) {
        if (userRepository.existsByEmail(email))     throw new IllegalArgumentException("EMAIL_DUPLICATE");
        if (userRepository.existsByUsername(username)) throw new IllegalArgumentException("USERNAME_DUPLICATE");
        String hash = passwordEncoder.encode(rawPassword);
        return userRepository.save(User.create(email, username, hash));
    }

    public String login(String username, String rawPassword) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("INVALID_CREDENTIALS"));
        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new IllegalArgumentException("INVALID_CREDENTIALS");
        }
        return jwt.generate(user.getId(), user.getUsername());
    }

    public boolean emailAvailable(String email)     { return !userRepository.existsByEmail(email); }
    public boolean usernameAvailable(String username){ return !userRepository.existsByUsername(username); }
}
