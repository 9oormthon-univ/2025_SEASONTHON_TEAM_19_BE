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

    // ---------- 여기부터 추가 ----------
    @ElementCollection(targetClass = LectureCategory.class)                      // [추가]
    @CollectionTable(name = "lecture_category_map",                              // [추가]
            joinColumns = @JoinColumn(name = "lecture_id"),
            indexes = {
                    @Index(name="idx_lecture_category_map_lecture", columnList = "lecture_id"),
                    @Index(name="idx_lecture_category_map_code",    columnList = "category_code")
            })
    @Column(name = "category_code", nullable = false, length = 20)               // [추가]
    @Enumerated(EnumType.STRING)                                                 // [추가]
    private java.util.Set<LectureCategory> categories = new java.util.HashSet<>(); // [추가]

    public void setCategories(java.util.Set<LectureCategory> newCats) {          // [추가]
        this.categories.clear();
        if (newCats != null) this.categories.addAll(newCats);
    }
    // ---------- 추가 끝 ----------


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
