package com.example.connect.mentor.api;

import com.example.connect.mentor.api.dto.MentorSummaryResponse;
import com.example.connect.mentor.application.MentorQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mentors")
@RequiredArgsConstructor
public class MentorQueryController {

    private final MentorQueryService mentorQueryService;

    // 추가
    @GetMapping
    public Page<MentorSummaryResponse> list(
            @PageableDefault(size = 20, sort = "id") Pageable pageable

    ) {
        return mentorQueryService.getMentors(pageable);
    }
}