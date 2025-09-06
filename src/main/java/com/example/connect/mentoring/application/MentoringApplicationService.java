package com.example.connect.mentoring.application;

import com.example.connect.mentoring.api.dto.MentoringApplicationCreateRequest;
import com.example.connect.mentoring.api.dto.MentoringApplicationResponse;
import com.example.connect.mentoring.domain.MentoringApplication;
import com.example.connect.mentoring.domain.MentoringApplicationRepository;
import com.example.connect.user.domain.User;
import com.example.connect.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class MentoringApplicationService {

    private final UserRepository userRepository;
    private final MentoringApplicationRepository applicationRepository;

    public MentoringApplicationResponse create(Long mentorId, MentoringApplicationCreateRequest req) {
        // 멘토 검증
        User mentor = userRepository.findByIdAndMentorTrue(mentorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않거나 멘토가 아닙니다."));

        // 신청 저장 (일시/생성시각 모두 문자열)
        MentoringApplication saved = applicationRepository.save(
                MentoringApplication.builder()
                        .mentor(mentor)
                        .applicantName(req.applicantName())
                        .phone(req.phone())
                        .scheduledAt(req.scheduledAt())
                        .content(req.content())
                        .createdAt(null) // @PrePersist에서 채움
                        .build()
        );

        // 멘토 누적 신청수 +1
        mentor.increaseMentoringCount();

        return MentoringApplicationResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public MentoringApplicationResponse get(Long applicationId) {
        var m = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "신청 내역이 없습니다."));
        return MentoringApplicationResponse.from(m);
    }
}
