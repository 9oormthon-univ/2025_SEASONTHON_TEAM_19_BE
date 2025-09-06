package com.example.connect.lecture.application;

import com.example.connect.lecture.api.dto.LectureCardRes;
import com.example.connect.lecture.api.dto.LectureCreateReq;
import com.example.connect.lecture.api.dto.LectureCreateRes;
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
import org.springframework.transaction.annotation.Transactional;
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

    // ---------- 여기부터 추가 ----------
    @Transactional
    public LectureCreateRes create(LectureCreateReq req) {
        // 카테고리 문자열 → Enum 세트로 파싱
        java.util.LinkedHashSet<LectureCategory> catSet = new java.util.LinkedHashSet<>();
        for (String raw : req.categories()) {
            if (raw == null || raw.isBlank()) continue;
            LectureCategory cat = parseCategory(raw);
            catSet.add(cat);
        }
        if (catSet.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "at least one category is required");
        }

        // 대표 카테고리(첫 번째) + 나머지 세트
        LectureCategory primary = catSet.iterator().next();

        Lecture lecture = new Lecture(
                primary,
                req.title(),
                req.date(),
                req.location(),
                req.capacity(),
                null,                // 썸네일은 선택사항
                req.content()
        );
        lecture.setCategories(catSet);       // [추가] 다중 카테고리 저장

        Lecture saved = lectureRepository.save(lecture);

        return new LectureCreateRes(
                saved.getId(),
                saved.getTitle(),
                saved.getCategories().stream().map(Enum::name).toList(), // [추가]
                saved.getDate(),
                saved.getLocation(),
                saved.getCapacity(),
                saved.getContent()
        );
    }

    private LectureCategory parseCategory(String s) {
        // slug 또는 enum name 둘 다 허용 (예: "ai" 또는 "AI")
        try {
            return LectureCategory.valueOf(s.trim().toUpperCase());
        } catch (Exception ignore) {
            return LectureCategory.from(s).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid category: " + s)
            );
        }
    }
    // ---------- 추가 끝 ----------

}