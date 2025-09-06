package com.example.connect.lecture.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationRes {
    private Long reservationId;
    private Long lectureId;
    private String message;
}
