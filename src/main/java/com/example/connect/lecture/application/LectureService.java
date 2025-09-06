package com.example.connect.lecture.application;

import com.example.connect.lecture.api.LectureCardRes;
import com.example.connect.lecture.domain.Lecture;
import com.example.connect.lecture.domain.LectureCategory;
import com.example.connect.lecture.domain.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    private long countReserved(Long lectureId) {
        return 0L; // 예약 테이블 붙이기 전까지 0으로
    }

    public Page<LectureCardRes> getLectureCards(LectureCategory category, Pageable pageable) {
        Page<Lecture> page = (category == null)
                ? lectureRepository.findAll(pageable)
                : lectureRepository.findByCategory(category, pageable);

        return page.map(l -> LectureCardRes.from(l, countReserved(l.getId())));
    }

    public LectureCardRes getLectureCard(Long id) {
        Lecture l = lectureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "lecture not found"));
        return LectureCardRes.from(l, countReserved(l.getId()));
    }
}