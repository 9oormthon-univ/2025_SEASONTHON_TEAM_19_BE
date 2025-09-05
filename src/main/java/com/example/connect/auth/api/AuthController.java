package com.example.connect.auth.api;

import com.example.connect.auth.application.AuthService;
import com.example.connect.common.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final JwtProvider jwt;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignupRes> signup(@RequestBody SignupReq req) {
        var u = service.signup(req.email(), req.username(), req.password());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SignupRes(u.getId(), u.getEmail(), u.getUsername()));
    }

    // 로그인
    @PostMapping("/login")
    public LoginRes login(@RequestBody LoginReq req) {
        String token = service.login(req.username(), req.password());
        return new LoginRes("Bearer", token, 3600);
    }

    // 중복 체크
    @GetMapping("/check/email")
    public CheckRes checkEmail(@RequestParam String value) {
        return new CheckRes(service.emailAvailable(value));
    }

    @GetMapping("/check/username")
    public CheckRes checkUsername(@RequestParam String value) {
        return new CheckRes(service.usernameAvailable(value));
    }

    // (옵션) 토큰으로 내 정보 확인
    @GetMapping("/me")
    public MeRes me(@RequestHeader(value = "Authorization", required = false) String auth) {
        String token = (auth != null && auth.startsWith("Bearer ")) ? auth.substring(7) : null;
        if (token == null || !jwt.validate(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Long userId = jwt.getUserId(token);
        String username = jwt.getUsername(token);
        return new MeRes(userId, username);
    }

    // ---- DTO ----
    public record SignupReq(@Email String email, @NotBlank String username, @NotBlank String password) {}
    public record SignupRes(Long id, String email, String username) {}
    public record LoginReq(@NotBlank String username, @NotBlank String password) {}
    public record LoginRes(String tokenType, String accessToken, int expiresIn) {}
    public record CheckRes(boolean available) {}
    public record MeRes(Long id, String username) {}
}
