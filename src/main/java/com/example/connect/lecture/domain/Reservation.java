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

    @ManyToOne(fetch = LAZY) @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @ManyToOne(fetch = LAZY) @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 20) // 예약 화면의 휴대폰 번호 등
    private String phone;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Reservation(Lecture lecture, User user, String phone) {
        this.lecture = lecture;
        this.user = user;
        this.phone = phone;
    }
}
