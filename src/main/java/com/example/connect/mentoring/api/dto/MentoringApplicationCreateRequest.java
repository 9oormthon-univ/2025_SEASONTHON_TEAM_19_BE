package com.example.connect.mentoring.api.dto;

public record MentoringApplicationCreateRequest(
        String applicantName,
        String phone,
        String scheduledAt,  // [변경:String] 예: "2025-09-07 15:00"
        String content
) {
}
