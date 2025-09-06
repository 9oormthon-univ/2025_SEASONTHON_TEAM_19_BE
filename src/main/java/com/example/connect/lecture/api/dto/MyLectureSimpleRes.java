package com.example.connect.lecture.api.dto;

import com.example.connect.lecture.domain.Lecture;

public record MyLectureSimpleRes(
        String title,
        String content,
        String category // 필요시 .slug()로 바꿔도 됨
) {
    public static MyLectureSimpleRes from(Lecture l) {
        return new MyLectureSimpleRes(
                l.getTitle(),
                l.getContent(),
                l.getCategory().name() // ← slug 원하면 l.getCategory().slug()
        );
    }
}
