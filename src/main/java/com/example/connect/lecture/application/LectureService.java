package com.example.connect.lecture.application;

import com.example.connect.lecture.api.LectureCardRes;
import com.example.connect.lecture.domain.LectureCategory;
import com.example.connect.lecture.domain.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureService {

    private final LectureRepository lectureRepository;

    public Page<LectureCardRes> getLectureCards(LectureCategory category, Pageable pageable) {
        return lectureRepository.findCards(category, pageable);
    }
}
