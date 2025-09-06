package com.example.connect.lecture.api;

import com.example.connect.lecture.domain.Lecture;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
public record LectureCardRes(
        Long id,
        String title,
        String category,    // 문자열로 내려줄거면 name()
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
        String location,
        int capacity,
        long reserved,
        int remain,
        String thumbnailUrl
) {
    public static LectureCardRes from(Lecture l, long reserved) {
        int remain = Math.max(0, l.getCapacity() - (int) reserved);
        return new LectureCardRes(
                l.getId(), l.getTitle(), l.getCategory().name(),
                l.getDate(), l.getLocation(), l.getCapacity(),
                reserved, remain, l.getThumbnailUrl()
        );
    }
}

