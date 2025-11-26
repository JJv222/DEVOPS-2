package com.piis.cinema.api.dto.mapper;

import com.piis.cinema.api.dto.ScreeningDto;
import com.piis.cinema.domain.Screening;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScreeningDtoMapper {
private final SeatDtoMapper seatDtoMapper;
    public ScreeningDto map(Screening screening) {
        return ScreeningDto.builder()
                .id(screening.getId())
                .durationMinutes(screening.getDurationMinutes())
                .filmTitle(screening.getFilm().getTitle())
                .filmImage(screening.getFilm().getImage())
                .roomName(screening.getRoom().getName())
                .seats(screening.getRoom().getSeats().stream().map(seatDtoMapper::map).toList())
                .dateTime(screening.getDateTime())
                .build();
    }

}
