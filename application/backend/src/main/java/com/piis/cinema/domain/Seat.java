package com.piis.cinema.domain;

import com.piis.cinema.infrastructure.database.entity.RoomEntity;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@With
@Builder
public class Seat {
    private Integer id;
    private RoomEntity room;
    private int rowNumber;
    private int seatNumber;
    private boolean occupied;
}
