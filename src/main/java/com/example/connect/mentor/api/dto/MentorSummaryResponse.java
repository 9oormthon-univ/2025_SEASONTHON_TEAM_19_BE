package com.example.connect.mentor.api.dto;

import com.example.connect.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorSummaryResponse {
    private Long id;
    private String username;
    private String email;   // 필요 시 노출
    private boolean mentor; // 항상 true

    public static MentorSummaryResponse from(User u) {
        return MentorSummaryResponse.builder()
                .id(u.getId())
                .username(u.getUsername())
                .email(u.getEmail())
                .mentor(u.isMentor())
                .build();
    }
}
