package com.example.connect.lecture.api.dto;

import com.example.connect.lecture.domain.Lecture;
import com.example.connect.lecture.domain.LectureCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class LectureDetailRes {
    private Long id;
    private LectureCategory category;
    private String title;
    private String content;
    private LocalDate date;
    private String location;
    private int capacity;
    private long reserved;
    private String thumbnailUrl;

    public static LectureDetailRes of(Lecture l, long reserved) {
        return new LectureDetailRes(
                l.getId(), l.getCategory(), l.getTitle(), l.getContent(),
                l.getDate(), l.getLocation(), l.getCapacity(), reserved,
                l.getThumbnailUrl()
        );
    }
}
