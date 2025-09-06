package com.example.connect.mentoring.api;

import com.example.connect.common.jwt.JwtProvider;
import com.example.connect.mentoring.api.dto.MentoringApplicationResponse;
import com.example.connect.mentoring.application.MentoringApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/my")
@RequiredArgsConstructor
public class MyMentoringApplicationController {

    private final MentoringApplicationService service;
    private final JwtProvider jwt;

    // 예) GET /api/my/mentoring-applications?page=0&size=20
    @GetMapping("/mentoring-applications")
    public Page<MentoringApplicationResponse> myApplications(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        }
        String token = authorization.substring(7);
        Long userId = jwt.getUserId(token);
        return service.listMy(userId, pageable);
    }
}

