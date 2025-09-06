package com.example.connect.lecture.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    long countByLectureId(Long lectureId);

    boolean existsByLectureIdAndUserId(Long lectureId, Long userId);


    @EntityGraph(attributePaths = "lecture") // ★ Lecture 함께 로딩
    Page<Reservation> findByUserId(Long userId, Pageable pageable);
}
