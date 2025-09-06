package com.example.connect.lecture.domain;

import com.example.connect.lecture.api.LectureCardRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends org.springframework.data.jpa.repository.JpaRepository<Lecture, Long> {

    @Query("""
        select new com.example.connect.lecture.api.LectureCardRes(
            l.id, l.title, l.category, l.date, l.location, l.capacity,
            count(r.id), l.thumbnailUrl
        )
        from Lecture l
        left join Reservation r on r.lecture = l
        where (:category is null or l.category = :category)
        group by l.id, l.title, l.category, l.date, l.location, l.capacity, l.thumbnailUrl
        """)
    Page<LectureCardRes> findCards(@Param("category") LectureCategory category, Pageable pageable);
}
