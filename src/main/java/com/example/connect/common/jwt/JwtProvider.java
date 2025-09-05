package com.example.connect.common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

public class JwtProvider {
    // 데모 비밀키: 배포 전 ENV로 빼고 훨씬 길게!
    private static final String SECRET = "ChangeMeToLongRandomSecretChangeMeToLongRandomSecret";
    private static final long EXPIRY_SECONDS = 60L * 60; // 1시간

    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public static String generate(Long userId, String username) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("username", username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(EXPIRY_SECONDS)))
                .signWith(KEY)
                .compact();
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
    }

    public Long getUserId(String token) {
        return Long.valueOf(parse(token).getBody().getSubject());
    }

    public String getUsername(String token) {
        return parse(token).getBody().get("username", String.class);
    }
}
