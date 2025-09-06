package com.example.connect.mentor.api.dto;

import com.example.connect.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorSummaryResponse {
    private Long id;
    private String username;
    private String email;   // 필요 시 노출
    private boolean mentor; // 항상 true

    private List<CategoryItem> categories; // [추가2]

    private String introduction; // [추가4] 리스트 응답에 자기소개 포함



    public static MentorSummaryResponse from(User u) {


        // [추가2]
        var cats = u.getCategories().stream()
                .map(cc -> new CategoryItem(cc.name(), cc.getDisplayName()))
                .toList();

        return MentorSummaryResponse.builder()
                .id(u.getId())
                .username(u.getUsername())
                .email(u.getEmail())
                .mentor(u.isMentor())
                .categories(cats) // [추가2]
                .introduction(u.getIntroduction())
                .build();
    }
}
