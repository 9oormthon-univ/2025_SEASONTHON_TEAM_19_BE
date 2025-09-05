package com.example.connect.user.application;
import com.example.connect.common.jwt.JwtProvider;
import com.example.connect.user.domain.User;
import com.example.connect.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(String email, String username, String rawPassword) {
        if (userRepository.existsByEmail(email))     throw new IllegalArgumentException("email duplicated");
        if (userRepository.existsByUsername(username)) throw new IllegalArgumentException("username duplicated");

        String hashed = passwordEncoder.encode(rawPassword);
        userRepository.save(User.builder()
                .email(email)
                .username(username)
                .password(hashed)
                .build());
    }

    public String login(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("bad credentials");
        }
        return JwtProvider.generate(user.getId(), user.getUsername());
    }
}
