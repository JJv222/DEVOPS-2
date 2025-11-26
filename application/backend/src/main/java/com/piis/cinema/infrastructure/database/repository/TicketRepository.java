package com.piis.cinema.infrastructure.database.repository;

import com.piis.cinema.business.dao.TicketDao;
import com.piis.cinema.domain.Screening;
import com.piis.cinema.domain.Seat;
import com.piis.cinema.domain.Ticket;
import com.piis.cinema.infrastructure.database.mapper.ScreeningEntityMapper;
import com.piis.cinema.infrastructure.database.mapper.SeatEntityMapper;
import com.piis.cinema.infrastructure.database.mapper.TicketEntityMapper;
import com.piis.cinema.infrastructure.database.repository.jpa.TicketJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TicketRepository implements TicketDao {

    private final TicketJpaRepository repository;
    private final TicketEntityMapper mapper;
    private final ScreeningEntityMapper screeningMapper;
    private final SeatEntityMapper seatEntityMapper;

    @Override
    public Ticket createTicket(Ticket ticket) {
        return mapper.map(repository.save(mapper.map(ticket)));
    }

    @Override
    public Optional<Ticket> getTicket(Integer ticketId) {
        return repository.findById(ticketId).map(mapper::map);
    }

    @Override
    public Optional<Ticket> findByScreeningAndSeat(Screening screening, Seat seat) {
        return repository.findByScreeningAndSeat(
                        screeningMapper.map(screening),
                        seatEntityMapper.map(seat))
                .map(mapper::map);
    }

    @Override
    public List<Ticket> findAllByScreening(Screening screening) {
        return repository.findAllByScreening(screeningMapper.map(screening))
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public Optional<Ticket> findByCode(String code) {
        return repository.findByReservationCode(code).map(mapper::map);
    }
}
