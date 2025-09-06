package com.example.connect.mentor.application;


import com.example.connect.mentor.api.dto.MentorSummaryResponse;
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


}