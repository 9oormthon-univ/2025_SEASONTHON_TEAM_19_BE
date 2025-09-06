package com.example.connect.user.api;


import com.example.connect.common.jwt.JwtProvider;
import com.example.connect.user.domain.User;
import com.example.connect.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final JwtProvider jwt;
    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(
            @RequestHeader(value = "Authorization", required = false) String authorization
    ) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        }
        String token = authorization.substring(7);
        Long userId = jwt.getUserId(token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        return ResponseEntity.ok(new MeResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        ));
    }

    public record MeResponse(Long id, String username, String email) {}
}