package com.example.connect.user.domain;

import com.example.connect.mentor.model.CategoryCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

    // mentor = true 인 사용자 페이징 조회
    Page<User> findByMentorTrue(Pageable pageable);

    // [추가2] 값 타입 컬렉션(Enum)으로 조인하여 필터링
    @Query("select u from User u join u.categories c " +
            "where u.mentor = true and c = :code")
    Page<User> findMentorsByCategory(@Param("code") CategoryCode code, Pageable pageable);
}
