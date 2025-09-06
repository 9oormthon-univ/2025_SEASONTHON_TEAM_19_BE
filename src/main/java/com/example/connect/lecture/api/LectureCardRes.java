package com.example.connect.lecture.api;

import com.example.connect.lecture.domain.LectureCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class LectureCardRes {
    private Long id;
    private String title;
    private LectureCategory category;
    private LocalDate date;
    private String location;
    private int capacity;
    private long reserved;        // 현재 예약 수
    private String thumbnailUrl;
}
