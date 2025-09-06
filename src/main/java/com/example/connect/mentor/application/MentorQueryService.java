package com.example.connect.mentor.application;


import com.example.connect.mentor.api.dto.MentorSummaryResponse;
import com.example.connect.mentor.model.CategoryCode;
import com.example.connect.user.domain.User;
import com.example.connect.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MentorQueryService {

    private final UserRepository userRepository;

    public Page<MentorSummaryResponse> getMentors(Pageable pageable) {
        return userRepository.findByMentorTrue(pageable)
                .map(MentorSummaryResponse::from);
    }

    // [추가2] 카테고리 필터 지원 오버로드
    public Page<MentorSummaryResponse> getMentors(String categoryCode, Pageable pageable) {
        Page<User> page;
        if (categoryCode == null || categoryCode.isBlank()) {
            page = userRepository.findByMentorTrue(pageable);
        } else {
            try {
                var code = CategoryCode.fromCode(categoryCode);
                page = userRepository.findMentorsByCategory(code, pageable);
            } catch (IllegalArgumentException e) {
                return Page.empty(pageable); // 잘못된 코드면 빈 페이지(정책에 따라 예외로 변경 가능)
            }
        }
        return page.map(MentorSummaryResponse::from);
    }


}