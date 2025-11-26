package com.piis.cinema.business.dao;

import com.piis.cinema.domain.Screening;
import com.piis.cinema.domain.Seat;
import com.piis.cinema.domain.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketDao {
    Ticket createTicket(Ticket ticket);

    Optional<Ticket> getTicket(Integer ticketId);

    Optional<Ticket> findByScreeningAndSeat(Screening screening, Seat seat);

    List<Ticket> findAllByScreening(Screening screening);

    Optional<Ticket> findByCode(String code);
}
