package com.example.connect.mentor.api.dto;

import java.util.List;

//추가2
public record RegisterMentorRequest(
        List<String> categoryCodes,
        String education,
        String career,
        String introduction    ) {
}
