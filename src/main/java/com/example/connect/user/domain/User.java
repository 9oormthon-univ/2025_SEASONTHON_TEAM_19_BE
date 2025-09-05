package com.example.connect.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "username")
        })

public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false) private String email;     // 이메일
    @Column(nullable=false) private String username;  // 아이디
    @Column(nullable=false) private String password;  // BCrypt 해시 저장

    @Builder
    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

}
