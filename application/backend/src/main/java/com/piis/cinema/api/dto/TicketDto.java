package com.piis.cinema.api.dto;

import com.piis.cinema.domain.TicketStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TicketDto {
    Integer id;
    String reservationCode;
    String film;
    String room;
    Integer row;
    Integer column;
    TicketStatus status;
}
