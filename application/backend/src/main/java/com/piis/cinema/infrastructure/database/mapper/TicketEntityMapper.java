package com.piis.cinema.infrastructure.database.mapper;

import com.piis.cinema.domain.*;
import com.piis.cinema.infrastructure.database.entity.*;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

@Component
public class TicketEntityMapper {
    public Ticket map(TicketEntity ticketEntity) {
        return Ticket.builder()
                .id(ticketEntity.getId())
                .seat(Seat
                        .builder()
                        .id(ticketEntity.getSeat().getId())
                        .rowNumber(ticketEntity.getSeat().getRowNumber())
                        .seatNumber(ticketEntity.getSeat().getSeatNumber())
                        .build()
                )
                .reservationCode(ticketEntity.getReservationCode())
                .screening(
                        Hibernate.isInitialized(ticketEntity.getScreening())
                                ? Screening
                                .builder()
                                .room(ticketEntity.getScreening().getRoom() != null ?
                                        Room.builder()
                                                .id(ticketEntity.getScreening().getRoom().getId())
                                                .name(ticketEntity.getScreening().getRoom().getName())
                                                .build() : null
                                )
                                .film(
                                        Hibernate.isInitialized(ticketEntity.getScreening().getFilm()) ?
                                                Film.builder()
                                                        .id(ticketEntity.getScreening().getFilm().getId())
                                                        .title(ticketEntity.getScreening().getFilm().getTitle())
                                                        .build()
                                                : null
                                )
                                .id(ticketEntity.getScreening().getId())
                                .build()
                                : null

                )
                .status(ticketEntity.getStatus())
                .build();
    }

    public TicketEntity map(Ticket ticket) {
        return TicketEntity.builder()
                .id(ticket.getId())
                .seat(SeatEntity
                        .builder()
                        .seatNumber(ticket.getSeat().getSeatNumber())
                        .rowNumber(ticket.getSeat().getRowNumber())
                        .id(ticket.getSeat().getId())
                        .build()
                )
                .screening(
                        ScreeningEntity
                                .builder()
                                .room(ticket.getScreening().getRoom() != null ?
                                        RoomEntity.builder()
                                                .id(ticket.getScreening().getRoom().getId())
                                                .name(ticket.getScreening().getRoom().getName())
                                                .build() : null
                                )
                                .film(ticket.getScreening().getFilm() != null ?
                                        FilmEntity.builder()
                                                .id(ticket.getScreening().getFilm().getId())
                                                .title(ticket.getScreening().getFilm().getTitle())
                                                .build() : null
                                )
                                .id(ticket.getScreening().getId())
                                .build())
                .reservationCode(ticket.getReservationCode())
                .status(ticket.getStatus())
                .build();
    }
}
