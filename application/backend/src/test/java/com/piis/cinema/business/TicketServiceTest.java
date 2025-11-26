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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketServiceTest {
    @Mock
    private TicketDao ticketDao;
    @Mock
    private TicketDtoMapper mapper;
    @Mock
    private SeatService seatService;
    @Mock
    private ScreeningService screeningService;
    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ticketService = new TicketService(ticketDao, mapper, seatService, screeningService);
    }

    @Test
    void createTicketShouldCreateTicket() {
        // given
        CreateTicketRequest request = new CreateTicketRequest(1, 2);
        Seat seat = mock(Seat.class);
        Screening screening = mock(Screening.class);
        Ticket savedTicket = mock(Ticket.class);
        TicketDto ticketDto = mock(TicketDto.class);

        when(seatService.getSeatById(1)).thenReturn(seat);
        when(screeningService.getScreening(2)).thenReturn(screening);
        when(ticketDao.findByScreeningAndSeat(screening, seat)).thenReturn(Optional.empty());
        when(ticketDao.createTicket(any(Ticket.class))).thenReturn(savedTicket);
        when(mapper.map(savedTicket)).thenReturn(ticketDto);

        // when
        TicketDto result = ticketService.createTicket(request);

        // then
        assertEquals(ticketDto, result);
        verify(ticketDao).createTicket(any(Ticket.class));
    }

    @Test
    void createTicketShouldThrowIfExists() {
        // given
        CreateTicketRequest request = new CreateTicketRequest(1, 2);
        Seat seat = mock(Seat.class);
        Screening screening = mock(Screening.class);
        Ticket ticket = mock(Ticket.class);

        when(seatService.getSeatById(1)).thenReturn(seat);
        when(screeningService.getScreening(2)).thenReturn(screening);
        when(ticketDao.findByScreeningAndSeat(any(), any())).thenReturn(Optional.of(ticket));

        // when & then
        assertThrows(ObjectAlreadyExistException.class, () -> ticketService.createTicket(request));
    }

    @Test
    void createTicketBulkShouldCreateTickets() {
        // given
        CreateTicketRequest req1 = new CreateTicketRequest(1, 2);
        CreateTicketRequest req2 = new CreateTicketRequest(3, 4);
        TicketDto dto1 = mock(TicketDto.class);
        TicketDto dto2 = mock(TicketDto.class);
        TicketService spyService = spy(ticketService);
        doReturn(dto1).when(spyService).createTicket(req1);
        doReturn(dto2).when(spyService).createTicket(req2);
        // when
        List<TicketDto> result = spyService.createTicketBulk(List.of(req1, req2));
        // then
        assertEquals(List.of(dto1, dto2), result);
    }

    @Test
    void updateStatusShouldUpdateStatus() {
        // given
        Ticket ticket = mock(Ticket.class);
        Ticket updatedTicket = mock(Ticket.class);
        TicketDto ticketDto = mock(TicketDto.class);
        when(ticketDao.getTicket(1)).thenReturn(Optional.of(ticket));
        when(ticket.getStatus()).thenReturn(TicketStatus.ACTIVE);
        when(ticket.withStatus(TicketStatus.USED)).thenReturn(updatedTicket);
        when(ticketDao.createTicket(updatedTicket)).thenReturn(updatedTicket);
        when(mapper.map(updatedTicket)).thenReturn(ticketDto);
        // when
        TicketDto result = ticketService.updateStatus(1);
        // then
        assertEquals(ticketDto, result);
    }

    @Test
    void updateStatusShouldThrowIfNotFound() {
        // given
        when(ticketDao.getTicket(1)).thenReturn(Optional.empty());
        // when & then
        assertThrows(NotFoundException.class, () -> ticketService.updateStatus(1));
    }

    @Test
    void getTicketsForScreeningShouldReturnTickets() {
        // given
        Screening screening = mock(Screening.class);
        Ticket ticket = mock(Ticket.class);
        TicketDto ticketDto = mock(TicketDto.class);
        when(screeningService.getScreening(1)).thenReturn(screening);
        when(ticketDao.findAllByScreening(screening)).thenReturn(List.of(ticket));
        when(mapper.map(ticket)).thenReturn(ticketDto);
        // when
        List<TicketDto> result = ticketService.getTicketsForScreening(1);
        // then
        assertEquals(List.of(ticketDto), result);
    }

    @Test
    void getTicketByCodeShouldReturnTicket() {
        // given
        Ticket ticket = mock(Ticket.class);
        TicketDto ticketDto = mock(TicketDto.class);
        when(ticketDao.findByCode("abc")).thenReturn(Optional.of(ticket));
        when(mapper.map(ticket)).thenReturn(ticketDto);
        // when
        TicketDto result = ticketService.getTicketByCode("abc");
        // then
        assertEquals(ticketDto, result);
    }

    @Test
    void getTicketByCodeShouldThrowIfNotFound() {
        // given
        when(ticketDao.findByCode("abc")).thenReturn(Optional.empty());
        // when & then
        assertThrows(NotFoundException.class, () -> ticketService.getTicketByCode("abc"));
    }
}

