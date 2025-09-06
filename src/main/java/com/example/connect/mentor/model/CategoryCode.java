package com.example.connect.mentor.model;

import lombok.Getter;

@Getter
public enum CategoryCode {
    AI("AI"),
    DIGITAL_UTIL("디지털활용"),
    HOBBY("취미"),
    JOB_WORK("취업 & 일"),
    CARE("돌봄"),
    EXERCISE("운동"),
    LIFE("생활"),
    MIND("마음");

    private final String displayName; // 한글/표시용

    CategoryCode(String displayName) {
        this.displayName = displayName;
    }

    public static CategoryCode fromCode(String code) {
        // 클라이언트에서 code로 Enum 이름(AI, DIGITAL_UTIL, ...)을 보낼 때 사용
        return CategoryCode.valueOf(code);
    }
}