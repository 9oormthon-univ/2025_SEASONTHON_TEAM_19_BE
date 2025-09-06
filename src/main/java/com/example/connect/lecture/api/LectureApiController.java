package com.example.connect.lecture.api;

import com.example.connect.lecture.api.dto.LectureCardRes;
import com.example.connect.lecture.application.LectureService;
import com.example.connect.lecture.domain.LectureCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
public class LectureApiController {

    private final LectureService lectureService;

    @GetMapping
    public Page<LectureCardRes> list(
            @RequestParam(required = false) String category,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable
    ) {
        var cat = LectureCategory.from(category).orElse(null);
        return lectureService.getLectureCards(cat, pageable);
    }

    @GetMapping("/{id}")
    public LectureCardRes detail(@PathVariable Long id) {
        return lectureService.getLectureCard(id);
    }
}
