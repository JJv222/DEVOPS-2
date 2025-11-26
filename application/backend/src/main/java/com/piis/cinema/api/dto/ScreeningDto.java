package com.piis.cinema.api.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class ScreeningDto {
    Integer id;
    LocalDateTime dateTime;
    String filmTitle;
    String roomName;
    String filmImage;
    int durationMinutes;
    List<SeatDto> seats;
}
