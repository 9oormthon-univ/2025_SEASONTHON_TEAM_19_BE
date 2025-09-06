package com.example.connect.lecture.application;

import com.example.connect.lecture.api.dto.LectureCardRes;
import com.example.connect.lecture.api.dto.LectureDetailRes;
import com.example.connect.lecture.domain.Lecture;
import com.example.connect.lecture.domain.LectureCategory;
import com.example.connect.lecture.domain.LectureRepository;
import com.example.connect.lecture.domain.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final ReservationRepository reservationRepository; // ← 추가

    /** 예약 수 집계 */
    private long countReserved(Long lectureId) {
        return reservationRepository.countByLectureId(lectureId);
    }

    /** 목록(카드) 조회 */
    public Page<LectureCardRes> getLectureCards(LectureCategory category, Pageable pageable) {
        Page<Lecture> page = (category == null)
                ? lectureRepository.findAll(pageable)
                : lectureRepository.findByCategory(category, pageable);

        return page.map(l -> LectureCardRes.from(l, countReserved(l.getId())));
    }

    /** 상세 조회(상세 DTO 리턴) */
    public LectureDetailRes getLectureDetail(Long id) {
        Lecture l = lectureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "lecture not found"));
        long reserved = countReserved(id);
        return LectureDetailRes.of(l, reserved);
    }

    // (원한다면 기존 메서드도 유지 가능)
    public LectureCardRes getLectureCard(Long id) {
        Lecture l = lectureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "lecture not found"));
        return LectureCardRes.from(l, countReserved(l.getId()));
    }
}