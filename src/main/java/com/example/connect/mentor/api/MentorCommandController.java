package com.example.connect.mentor.api;

import com.example.connect.mentor.api.dto.RegisterMentorRequest;
import com.example.connect.mentor.application.MentorCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mentors")
public class MentorCommandController {
    private final MentorCommandService mentorCommandService;

    @PostMapping("/{userId}/register") // 멘토 등록 + 카테고리 지정
    public void registerAsMentor(@PathVariable Long userId,
                                 @RequestBody RegisterMentorRequest req) {
        mentorCommandService.registerAsMentor(userId, req.categoryCodes(),req.education(), req.career(),req.introduction() );
    }

    @PostMapping("/{userId}/categories") // 멘토 카테고리 교체
    public void replaceCategories(@PathVariable Long userId,
                                  @RequestBody RegisterMentorRequest req) {
        mentorCommandService.replaceCategories(userId, req.categoryCodes());
    }
}
