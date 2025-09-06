package com.example.connect.mentoring.domain;

import com.example.connect.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mentoring_application",
        indexes = {
                @Index(name="idx_ma_mentor_id", columnList = "mentor_id"),
                @Index(name="idx_ma_created_at", columnList = "created_at")
        })
public class MentoringApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 신청 대상 멘토
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mentor_id", nullable = false)
    private User mentor;

    @Column(nullable = false, length = 60)
    private String applicantName;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, name = "scheduled_at", length = 40) // [변경:String]
    private String scheduledAt; // [변경:String] 사용자 입력 일시(문자열)

    @Column(columnDefinition = "TEXT", nullable = true)
    private String content;

    @Column(nullable = false, name = "created_at", length = 40)   // [변경:String]
    private String createdAt;  // [변경:String] 서버가 세팅하는 생성시각(문자열)

    @Builder
    private MentoringApplication(User mentor, String applicantName, String phone,
                                 String scheduledAt, String content, String createdAt) { // [변경:String]
        this.mentor = mentor;
        this.applicantName = applicantName;
        this.phone = phone;
        this.scheduledAt = scheduledAt;
        this.content = content;
        this.createdAt = createdAt; // null이면 @PrePersist에서 채움
    }

    @PrePersist // [추가] createdAt 자동 문자열 세팅
    private void onCreate() {
        if (this.createdAt == null || this.createdAt.isBlank()) {
            this.createdAt = java.time.OffsetDateTime.now().toString(); // 예: 2025-09-07T10:12:45+09:00
        }
    }
}
