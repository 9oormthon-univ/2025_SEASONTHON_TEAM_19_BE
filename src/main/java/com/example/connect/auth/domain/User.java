package com.example.connect.auth.domain;

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

    @Builder
    private User(String email, String username, String passwordHash) {
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public static User create(String email, String username, String passwordHash) {
        return User.builder().email(email).username(username).passwordHash(passwordHash).build();
    }
}
