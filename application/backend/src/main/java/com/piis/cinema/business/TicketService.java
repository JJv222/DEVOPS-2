package com.piis.cinema.business;

import com.piis.cinema.api.dto.TicketDto;
import com.piis.cinema.api.dto.mapper.TicketDtoMapper;
import com.piis.cinema.api.request.CreateTicketRequest;
import com.piis.cinema.business.dao.TicketDao;
import com.piis.cinema.domain.Exception.NotFoundException;
import com.piis.cinema.domain.Exception.ObjectAlreadyExistException;
import com.piis.cinema.domain.Screening;
import com.piis.cinema.domain.Seat;
import com.piis.cinema.domain.Ticket;
import com.piis.cinema.domain.TicketStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketDao ticketDao;
    private final TicketDtoMapper mapper;
    private final SeatService seatService;
    private final ScreeningService screeningService;

    @Transactional
    public TicketDto createTicket(CreateTicketRequest request) {
        Seat seat = seatService.getSeatById(request.seatId());
        Screening screening = screeningService.getScreening(request.screeningId());
        ticketDao.findByScreeningAndSeat(screening, seat)
                .ifPresent(t -> { throw new ObjectAlreadyExistException("Ticket for this screening and seat already exists!"); });
        Ticket ticket = buildTicket(screening, seat);
        return mapper.map(ticketDao.createTicket(ticket));
    }

    @Transactional
    public List<TicketDto> createTicketBulk(final List<CreateTicketRequest> request){
        return request.stream()
                .map(this::createTicket)
                .toList();
    }

    private Ticket buildTicket(Screening screening, Seat seat) {
        return Ticket.builder()
                .seat(seat)
                .status(TicketStatus.ACTIVE)
                .screening(screening)
                .reservationCode(generateCode(OffsetDateTime.now()))
                .build();
    }

    @Transactional
    public TicketDto updateStatus(Integer ticketId) {
        Ticket ticket = ticketDao.getTicket(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket with this id does not exist!"));
        TicketStatus nextStatus = getNextStatus(ticket.getStatus());
        Ticket updatedTicket = ticketDao.createTicket(ticket.withStatus(nextStatus));
        return mapper.map(updatedTicket);
    }

    @Transactional
    public List<TicketDto> getTicketsForScreening(Integer screeningId) {
        Screening screening = screeningService.getScreening(screeningId);
        return ticketDao.findAllByScreening(screening).stream().map(mapper::map).toList();
    }

    public TicketDto getTicketByCode(String code) {
        return mapper.map(
                ticketDao.findByCode(code)
                        .orElseThrow(() -> new NotFoundException("Ticket with this code do not exist!"))
        );
    }

    private TicketStatus getNextStatus(TicketStatus status) {
        return switch (status) {
            case ACTIVE -> TicketStatus.USED;
            case USED -> TicketStatus.INACTIVE;
            default -> status;
        };
    }

    private String generateCode(OffsetDateTime when) {
        return String.format("%d%d%d%d%d%d%d",
                when.getMonth().ordinal(),
                when.getYear(),
                when.getHour(),
                when.getDayOfMonth(),
                when.getSecond(),
                when.getMinute(),
                new Random().nextInt(90) + 10
        );
    }
}
