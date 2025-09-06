package com.example.connect.lecture.application;

import com.example.connect.lecture.api.dto.ReservationReq;
import com.example.connect.lecture.api.dto.ReservationRes;
import com.example.connect.lecture.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final LectureRepository lectureRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationRes reserve(Long lectureId, Long userId, ReservationReq req) {
        var lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "lecture not found"));

        // 중복 예약 방지
        if (reservationRepository.existsByLectureIdAndUserId(lectureId, userId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "already reserved");
        }

        long reserved = reservationRepository.countByLectureId(lectureId);
        if (reserved >= lecture.getCapacity()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "lecture is full");
        }

        var saved = reservationRepository.save(
                new Reservation(lecture, userId, req.getName(), req.getPhone())
        );
        return new ReservationRes(saved.getId(), lectureId, "reserved");
    }

    @Transactional(readOnly = true)
    public Page<Reservation> getMyReservations(Long userId, Pageable pageable) {
        return reservationRepository.findByUserId(userId, pageable);
    }
}
