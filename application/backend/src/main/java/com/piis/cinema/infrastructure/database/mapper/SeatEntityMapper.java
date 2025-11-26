package com.piis.cinema.infrastructure.database.mapper;

import com.piis.cinema.domain.Seat;
import com.piis.cinema.infrastructure.database.entity.SeatEntity;
import org.springframework.stereotype.Component;

@Component
public class SeatEntityMapper {
    public Seat map(SeatEntity seatEntity) {
        return Seat.builder()
                .id(seatEntity.getId())
                .seatNumber(seatEntity.getSeatNumber())
                .rowNumber(seatEntity.getRowNumber())
                .build();
    }

    public SeatEntity map(Seat seat) {
        return SeatEntity.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .rowNumber(seat.getRowNumber())
                .build();
    }
}
