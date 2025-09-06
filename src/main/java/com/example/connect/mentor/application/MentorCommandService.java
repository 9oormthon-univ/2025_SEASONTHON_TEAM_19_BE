package com.example.connect.mentor.application;

import com.example.connect.mentor.model.CategoryCode;
import com.example.connect.user.domain.User;
import com.example.connect.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MentorCommandService {
    private final UserRepository userRepository;

    public void registerAsMentor(Long userId, java.util.List<String> categoryCodes,String education, String career, String introduction) {
        User u = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자"));
        u.registerAsMentor();
        Set<CategoryCode> codes = toCodes(categoryCodes);
        u.setCategories(codes);
        u.updateMentorProfile(education, career, introduction); // [추가3]
    }

    public void replaceCategories(Long userId, java.util.List<String> categoryCodes) {
        User u = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자"));
        Set<CategoryCode> codes = toCodes(categoryCodes);
        u.setCategories(codes);
    }

    private Set<CategoryCode> toCodes(java.util.List<String> categoryCodes) {
        if (categoryCodes == null) return java.util.Set.of();
        return categoryCodes.stream()
                .map(CategoryCode::fromCode)  // 문자열 → Enum
                .collect(Collectors.toSet());
    }
}