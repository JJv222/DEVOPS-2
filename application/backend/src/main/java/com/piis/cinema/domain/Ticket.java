package com.piis.cinema.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@Builder
public class Ticket {
    Integer id;
    Screening screening;
    Seat seat;
    String reservationCode;
    TicketStatus status;
}
