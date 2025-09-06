package com.example.connect.mentoring.api.dto;

import com.example.connect.mentoring.domain.MentoringApplication;
import lombok.Builder;

@Builder
public record MentoringApplicationResponse(
        Long id,
        Long mentorId,
        String mentorUsername,
        String applicantName,
        String phone,
        String scheduledAt,  // [변경:String]
        String content,
        String createdAt     // [변경:String]
) {
    public static MentoringApplicationResponse from(MentoringApplication m) {
        return MentoringApplicationResponse.builder()
                .id(m.getId())
                .mentorId(m.getMentor().getId())
                .mentorUsername(m.getMentor().getUsername())
                .applicantName(m.getApplicantName())
                .phone(m.getPhone())
                .scheduledAt(m.getScheduledAt())
                .content(m.getContent())
                .createdAt(m.getCreatedAt())
                .build();
    }
}
