package com.example.connect.mentor.api;

import com.example.connect.mentor.api.dto.CategoryItem;
import com.example.connect.mentor.api.dto.MentorDetailResponse;
import com.example.connect.mentor.api.dto.MentorSummaryResponse;
import com.example.connect.mentor.application.MentorQueryService;
import com.example.connect.mentor.model.CategoryCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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

    // [추가] 선택한 카테고리에 해당하는 멘토 리스트
    //  예) GET /api/mentors/by-category?categoryCode=AI
    @GetMapping("/by-category")
    public Page<MentorSummaryResponse> listByCategory(
            @RequestParam String categoryCode,                           // [추가]
            @PageableDefault(size = 20, sort = "id") Pageable pageable   // [추가]
    ) {
        return mentorQueryService.getMentors(categoryCode, pageable);     // [추가]
    }

    // [추가] 카테고리 버튼 목록(API): Enum → 코드/표시명으로 내려줌
    @GetMapping("/categories")
    public List<CategoryItem> categories() {
        return Arrays.stream(CategoryCode.values())
                .map(cc -> new CategoryItem(cc.name(), cc.getDisplayName()))
                .toList();
    }

    @GetMapping("/{mentorId}")
    public MentorDetailResponse detail(@PathVariable Long mentorId) { // [추가]
        return mentorQueryService.getMentorDetail(mentorId);
    }

}
