package com.example.connect.lecture.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReservationReq {
    @NotBlank private String name;
    @NotBlank private String phone;
}
