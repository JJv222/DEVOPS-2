package com.piis.cinema.infrastructure.database.mapper;

import com.piis.cinema.domain.Film;
import com.piis.cinema.domain.Room;
import com.piis.cinema.domain.Screening;
import com.piis.cinema.infrastructure.database.entity.FilmEntity;
import com.piis.cinema.infrastructure.database.entity.RoomEntity;
import com.piis.cinema.infrastructure.database.entity.ScreeningEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ScreeningEntityMapper {
    private final SeatEntityMapper seatEntityMapper;
    private final TicketEntityMapper ticketEntityMapper;

    public Screening map(ScreeningEntity screeningEntity) {
        return Screening.builder()
                .id(screeningEntity.getId())
                .film(
                        Film.builder()
                                .id(screeningEntity.getFilm().getId())
                                .image(screeningEntity.getFilm().getImage())
                                .title(screeningEntity.getFilm().getTitle())
                                .description(screeningEntity.getFilm().getDescription())
                                .build()
                )
                .room(
                        Room.builder()
                                .id(screeningEntity.getRoom().getId())
                                .seats(
                                        Hibernate.isInitialized(screeningEntity.getTickets())
                                                ?  screeningEntity.getRoom().getSeats().stream().map(seatEntityMapper::map).toList()
                                                : List.of()
                                )
                                .name(screeningEntity.getRoom().getName())
                                .build()
                )
                .tickets(
                        Hibernate.isInitialized(screeningEntity.getTickets())
                                ? screeningEntity.getTickets().stream().map(ticketEntityMapper::map).toList()
                                : List.of()
                )
                .durationMinutes(screeningEntity.getDurationMinutes())
                .dateTime(screeningEntity.getDateTime())
                .build();
    }

    public ScreeningEntity map(Screening screening) {
        return ScreeningEntity.builder()
                .id(screening.getId())
                .film(
                        FilmEntity.builder()
                                .id(screening.getFilm().getId())
                                .image(screening.getFilm().getImage())
                                .title(screening.getFilm().getTitle())
                                .description(screening.getFilm().getDescription())
                                .build()
                )
                .durationMinutes(screening.getDurationMinutes())
                .room(
                        RoomEntity.builder()
                                .id(screening.getRoom().getId())
                                .seats(Set.of())
                                .name(screening.getRoom().getName())
                                .build()
                )
                .tickets(Set.of())
                .dateTime(screening.getDateTime())
                .build();
    }
}
