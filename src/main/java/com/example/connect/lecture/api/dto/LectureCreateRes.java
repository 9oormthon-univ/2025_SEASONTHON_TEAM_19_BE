package com.example.connect.lecture.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record LectureCreateRes(
        Long id,
        String title,
        List<String> categories,                      // [추가] 여러 개
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        String location,
        int capacity,
        String content
) {}
