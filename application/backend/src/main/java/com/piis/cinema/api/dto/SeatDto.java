package com.piis.cinema.api.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SeatDto {
    Integer id;
    int rowNumber;
    int seatNumber;
    boolean occupied;
}
