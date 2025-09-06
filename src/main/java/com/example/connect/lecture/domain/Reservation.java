package com.example.connect.lecture.domain;

import com.example.connect.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "reservations", indexes = {
        @Index(name = "idx_reservation_lecture", columnList = "lecture_id")
})
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 강연 */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    /** 예약자(로그인 사용자) ID - User 테이블 FK는 아니고 값만 저장 */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /** 예약자 표시용 */
    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 20)
    private String phone;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Reservation(Lecture lecture, Long userId, String name, String phone) {
        this.lecture = lecture;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
    }
}
