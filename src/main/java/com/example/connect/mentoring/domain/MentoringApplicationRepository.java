package com.example.connect.mentoring.domain;

import com.example.connect.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentoringApplicationRepository extends JpaRepository<MentoringApplication, Long> {
    Page<MentoringApplication> findByMentor(User mentor, Pageable pageable);
}