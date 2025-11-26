package com.piis.cinema.api.dto.mapper;

import com.piis.cinema.api.dto.SeatDto;
import com.piis.cinema.domain.Seat;
import org.springframework.stereotype.Component;

@Component
public class SeatDtoMapper {
    public Seat map(SeatDto seat) {
        return Seat.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .rowNumber(seat.getRowNumber())
                .occupied(seat.isOccupied())
                .build();
    }

    public SeatDto map(Seat seat) {
        return SeatDto.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .rowNumber(seat.getRowNumber())
                .occupied(seat.isOccupied())
                .build();
    }
}
