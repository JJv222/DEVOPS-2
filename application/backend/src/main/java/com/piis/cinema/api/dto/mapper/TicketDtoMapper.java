package com.piis.cinema.api.dto.mapper;

import com.piis.cinema.api.dto.TicketDto;
import com.piis.cinema.domain.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketDtoMapper {

    public TicketDto map(Ticket ticket) {
        return TicketDto.builder()
                .id(ticket.getId())
                .status(ticket.getStatus())
                .film(ticket.getScreening().getFilm().getTitle())
                .room(ticket.getScreening().getRoom().getName())
                .column(ticket.getSeat().getSeatNumber())
                .row(ticket.getSeat().getRowNumber())
                .reservationCode(ticket.getReservationCode())
                .build();
    }
}
