package com.example.connect.lecture.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "lectures",
        indexes = {
                @Index(name = "idx_lectures_category", columnList = "category"),
                @Index(name = "idx_lectures_date",     columnList = "date")
        })
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Lecture {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private LectureCategory category;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "text")
    private String content;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, length = 120)
    private String location;

    @Column(nullable = false)
    private int capacity;

    @Column(length = 255)
    private String thumbnailUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // 생성자(필수 필드만)
    public Lecture(LectureCategory category, String title, LocalDate date,
                   String location, int capacity, String thumbnailUrl, String content) {
        this.category = category;
        this.title = title;
        this.date = date;
        this.location = location;
        this.capacity = capacity;
        this.thumbnailUrl = thumbnailUrl;
        this.content = content;
    }
}
