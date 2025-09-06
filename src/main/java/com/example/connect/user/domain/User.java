package com.example.connect.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users",
        indexes = {@Index(name="idx_users_username", columnList = "username", unique = true),
                @Index(name="idx_users_email",    columnList = "email",    unique = true)})
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length = 120)
    private String email;

    @Column(nullable=false, unique=true, length = 50)
    private String username;

    @Column(nullable=false, length = 120)
    private String passwordHash;

    // 멘토 등록 여부(기본 false). MySQL이면 columnDefinition으로 DB 기본값까지 명시.
    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0") // [변경]
    private boolean mentor = false;

    @Builder
    // [변경] mentor 파라미터 추가
    private User(String email, String username, String passwordHash, boolean mentor) { // [변경]
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.mentor = mentor; // [추가]
    }

    public static User create(String email, String username, String passwordHash) {
        return User.builder().email(email).username(username).passwordHash(passwordHash).build();
    }
    // 이후 멘토 등록 시 호출
    public void registerAsMentor() {
        this.mentor = true;
    }
}
