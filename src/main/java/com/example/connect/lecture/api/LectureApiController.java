package com.example.connect.lecture.api;

import com.example.connect.lecture.application.LectureService;
import com.example.connect.lecture.domain.LectureCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        LectureCategory cat = LectureCategory.from(category)
                .orElse(null); // null이면 전체
        return lectureService.getLectureCards(cat, pageable);
    }

    @GetMapping("/{id}")
    public LectureCardRes detail(@PathVariable Long id) {
        return lectureService.getLectureCard(id);
    }
}
