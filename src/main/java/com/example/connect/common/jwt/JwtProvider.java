package com.example.connect.common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtProvider {


    private final Key KEY;
    private final long EXPIRY_SECONDS;

    public JwtProvider(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiry-seconds:3600}") long expirySeconds
    ) {
        this.KEY = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.EXPIRY_SECONDS = expirySeconds;
    }


    /** 토큰 생성 */
    public String generate(Long userId, String username) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(String.valueOf(userId))              // userId는 subject 로
                .claim("username", username)                     // username 는 claim 으로
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(EXPIRY_SECONDS)))
                .signWith(KEY, SignatureAlgorithm.HS256)         // jjwt 0.11.5
                .compact();
    }

    /** ====== 컨트롤러에서 필요한 메서드들 ====== */
    public Long getUserId(String token) {
        return Long.valueOf(parse(token).getSubject());
    }

    public String getUsername(String token) {
        return parse(token).get("username", String.class);
    }

    public boolean validate(String token) {
        try {
            parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /** 내부 공용 파서 */
    private Claims parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
