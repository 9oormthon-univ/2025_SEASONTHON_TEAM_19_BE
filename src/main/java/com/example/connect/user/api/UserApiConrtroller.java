package com.example.connect.user.api;

import com.example.connect.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserApiConrtroller {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req) {
        userService.signup(req.email(), req.username(), req.password());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        String token = userService.login(req.username(), req.password());
        return ResponseEntity.ok(new LoginResponse("Bearer " + token));
    }

    // ---- DTOs (간단히 내부클래스로, 원하면 user.api.dto 패키지로 분리) ----
    public record SignupRequest(String email, String username, String password) {}
    public record LoginRequest(String username, String password) {}
    public record LoginResponse(String accessToken) {}
}
