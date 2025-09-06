package com.example.connect.lecture.domain;

import java.util.Arrays;
import java.util.Optional;

public enum LectureCategory {
    AI("ai"),
    DIGITAL("digital"),          // 디지털활용
    HOBBY("hobby"),              // 취미
    JOB("job"),                  // 취업&일
    CARE("care"),                // 돌봄
    EXERCISE("exercise"),        // 운동
    LIFE("life"),                // 생활
    MIND("mind");                // 마음

    private final String slug;
    LectureCategory(String slug) { this.slug = slug; }
    public String slug() { return slug; }

    public static Optional<LectureCategory> from(String s) {
        if (s == null || s.isBlank()) return Optional.empty();
        String x = s.trim().toLowerCase();
        return Arrays.stream(values())
                .filter(v -> v.slug.equals(x))
                .findFirst();
    }
}
