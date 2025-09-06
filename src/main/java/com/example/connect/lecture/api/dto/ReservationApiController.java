package com.example.connect.lecture.api;

import com.example.connect.lecture.api.dto.ReservationReq;
import com.example.connect.lecture.api.dto.ReservationRes;
import com.example.connect.lecture.application.ReservationService;
import com.example.connect.common.jwt.JwtProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReservationApiController {

    private final ReservationService reservationService;
    private final JwtProvider jwt;

    /** 강연 예약 생성 */
    @PostMapping("/api/lectures/{lectureId}/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationRes reserve(
            @PathVariable Long lectureId,
            @RequestHeader("Authorization") String auth,
            @Valid @RequestBody ReservationReq req
    ) {
        // "Bearer x.y.z" → 토큰만 추출
        String token = (auth != null && auth.startsWith("Bearer ")) ? auth.substring(7) : auth;
        Long userId = jwt.getUserId(token);
        return reservationService.reserve(lectureId, userId, req);
    }

    /** 내 예약 목록 */
    @GetMapping("/api/my/reservations")
    public Page<?> myReservations(
            @RequestHeader("Authorization") String auth,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        String token = (auth != null && auth.startsWith("Bearer ")) ? auth.substring(7) : auth;
        Long userId = jwt.getUserId(token);
        return reservationService.getMyReservations(userId, pageable)
                .map(r -> new ReservationRes(r.getId(), r.getLecture().getId(), "ok"));
    }
}
