package com.example.connect.mentoring.api;


import com.example.connect.mentoring.api.dto.MentoringApplicationCreateRequest;
import com.example.connect.mentoring.application.MentoringApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.connect.mentoring.api.dto.MentoringApplicationResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mentors")
public class MentoringApplicationController {

    private final MentoringApplicationService service;

    // 멘토링 신청 생성 (멘토 id와 함께)
    // 예) POST /api/mentors/7/applications
    @PostMapping("/{mentorId}/applications") // [신규]
    public MentoringApplicationResponse apply(@PathVariable Long mentorId,
                                              @RequestBody MentoringApplicationCreateRequest req) {
        return service.create(mentorId, req);
    }

    // 신청 단건 조회 (완료 화면에서 사용)
    // 예) GET /api/mentors/applications/100
    @GetMapping("/applications/{applicationId}") // [신규]
    public MentoringApplicationResponse get(@PathVariable Long applicationId) {
        return service.get(applicationId);
    }
}

