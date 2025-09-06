package com.example.connect.mentoring.api;


import com.example.connect.mentoring.api.dto.MentoringApplicationCreateRequest;
import com.example.connect.mentoring.application.MentoringApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.connect.mentoring.api.dto.MentoringApplicationResponse;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mentors")
public class MentoringApplicationController {

    private final MentoringApplicationService service;
    private final com.example.connect.common.jwt.JwtProvider jwt; // ★ 추가

    // 멘토링 신청 생성 (멘토 id와 함께)
    // 예) POST /api/mentors/7/applications
    @PostMapping("/{mentorId}/applications") // [신규]
    public MentoringApplicationResponse apply(@PathVariable Long mentorId,
                                              @RequestHeader(value = "Authorization", required = false) String authorization,
                                              @RequestBody MentoringApplicationCreateRequest req) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        }
        String token = authorization.substring(7);
        Long applicantUserId = jwt.getUserId(token); // ★ 본인 ID

        return service.create(mentorId,applicantUserId, req);
    }

    // 신청 단건 조회 (완료 화면에서 사용)
    // 예) GET /api/mentors/applications/100
    @GetMapping("/applications/{applicationId}") // [신규]
    public MentoringApplicationResponse get(@PathVariable Long applicationId) {
        return service.get(applicationId);
    }
}

