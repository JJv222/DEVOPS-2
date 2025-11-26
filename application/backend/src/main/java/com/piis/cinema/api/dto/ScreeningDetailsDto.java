package com.piis.cinema.api.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ScreeningDetailsDto {
    Integer id;
    LocalDateTime dateTime;
    String filmTitle;
    String roomName;
    int durationMinutes;
}
