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
public class MentorDetailResponse {                 // [추가]
    private Long id;                                // [추가]
    private String username;                        // [추가] 멘토 이름
    private List<CategoryItem> categories;          // [추가]
    private String education;                       // [추가]
    private String career;                          // [추가]
    private String introduction;                    // [추가]

    public static MentorDetailResponse from(User u) { // [추가]
        var cats = u.getCategories().stream()
                .map(cc -> new CategoryItem(cc.name(), cc.getDisplayName()))
                .toList();

        return MentorDetailResponse.builder()
                .id(u.getId())
                .username(u.getUsername())
                .categories(cats)
                .education(u.getEducation())
                .career(u.getCareer())
                .introduction(u.getIntroduction())
                .build();
    }
}