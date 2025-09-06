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

    // 잘못된 카테고리 문자열을 400으로 처리하고 싶다면 아래처럼:
    // LectureCategory.from(category).orElseThrow(() ->
    //         new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid category"));
}
