package com.example.connect.common.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

public class JwtProvider {

    // 데모용 하드코딩 (추후 yml 로 빼기)
    private static final String SECRET = "ChangeMeToLongRandomSecretChangeMeToLongRandomSecret";
    private static final long   EXPIRY_SECONDS = 60 * 60; // 1시간

    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public static String generate(Long userId, String username) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("username", username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(EXPIRY_SECONDS)))
                .signWith(KEY)
                .compact();
    }
}
