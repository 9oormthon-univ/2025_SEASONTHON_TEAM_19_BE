package com.example.connect.lecture.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record LectureCreateReq(
        @NotBlank String title,
        @NotEmpty List<String> categories,            // [추가] 여러 개
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        @NotBlank String location,
        @Min(1) int capacity,
        @NotBlank String content
) {}
