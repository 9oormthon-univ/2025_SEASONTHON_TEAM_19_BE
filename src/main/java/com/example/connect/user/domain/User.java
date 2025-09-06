package com.example.connect.user.domain;

import com.example.connect.mentor.model.CategoryCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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

    // [추가2] 고정 카테고리(Enum)를 별도 테이블 없이 값 컬렉션으로 저장
    @ElementCollection(targetClass = CategoryCode.class)                // [추가]
    @CollectionTable(name = "user_category",                            // [추가] 조인 테이블(값 타입)
            joinColumns = @JoinColumn(name = "user_id"),
            indexes = {
                    @Index(name="idx_user_category_user", columnList = "user_id"),
                    @Index(name="idx_user_category_code", columnList = "category_code")
            })
    @Column(name = "category_code", nullable = false, length = 40)      // [추가] 열 이름 지정
    @Enumerated(EnumType.STRING)                                        // [추가] 문자열로 저장
    private Set<CategoryCode> categories = new HashSet<>();

    // ---------- 프로필 확장 ----------
    @Column(columnDefinition = "TEXT", nullable = true)     // [추가3]
    private String education;                               // [추가3]

    @Column(columnDefinition = "TEXT", nullable = true)     // [추가3]
    private String career;                                  // [추가3]

    @Column(columnDefinition = "TEXT", nullable = true)     // [추가3]
    private String introduction;                            // [추가3]



    @Builder
    // [변경] mentor 파라미터 추가
    private User(String email, String username, String passwordHash, boolean mentor, Set<CategoryCode> categories
    ,String education, String career, String introduction) { // [변경,추가2]
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.mentor = mentor; // [추가]
        if (categories != null) this.categories = categories; // [추가2]
        this.education = education;         // [추가3]
        this.career = career;               // [추가3]
        this.introduction = introduction;   // [추가3]
    }

    public static User create(String email, String username, String passwordHash) {
        return User.builder().email(email).username(username).passwordHash(passwordHash).build();
    }
    // 이후 멘토 등록 시 호출
    public void registerAsMentor() {
        this.mentor = true;
    }

    // [추가2] 멘토 카테고리 교체(덮어쓰기)
    public void setCategories(Set<CategoryCode> newCategories) {
        this.categories.clear();
        this.categories.addAll(newCategories);
    }

    // [추가3] 멘토 프로필 3종 갱신
    public void updateMentorProfile(String education, String career, String introduction) { // [추가3]
        this.education = education;         // [추가3]
        this.career = career;               // [추가3]
        this.introduction = introduction;   // [추가3]
    }


}
