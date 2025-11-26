package com.piis.cinema.business;

import com.piis.cinema.api.dto.ScreeningDto;
import com.piis.cinema.api.dto.mapper.ScreeningDtoMapper;
import com.piis.cinema.api.request.CreateScreeningRequest;
import com.piis.cinema.business.dao.ScreeningDao;
import com.piis.cinema.domain.Exception.NotFoundException;
import com.piis.cinema.domain.Room;
import com.piis.cinema.domain.Screening;
import com.piis.cinema.domain.Seat;
import com.piis.cinema.domain.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScreeningServiceTest {
    @Mock
    private ScreeningDao screeningDao;
    @Mock
    private ScreeningDtoMapper mapper;
    @InjectMocks
    private ScreeningService screeningService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        screeningService = new ScreeningService(screeningDao, mapper);
    }

    @Test
    void getScreeningsShouldReturnPage() {
        // given
        Screening screening = mock(Screening.class);
        ScreeningDto dto = mock(ScreeningDto.class);
        Page<Screening> page = new PageImpl<>(List.of(screening));
        when(screeningDao.find(any(Pageable.class))).thenReturn(page);
        when(mapper.map(screening)).thenReturn(dto);
        // when
        Page<ScreeningDto> result = screeningService.getScreenings(0, 10);
        // then
        assertEquals(1, result.getTotalElements());
        assertEquals(dto, result.getContent().getFirst());
    }

    @Test
    void getScreeningShouldReturnScreening() {
        // given
        Screening screening = mock(Screening.class);
        when(screeningDao.getById(1)).thenReturn(Optional.of(screening));
        // when
        Screening result = screeningService.getScreening(1);
        // then
        assertEquals(screening, result);
    }

    @Test
    void getScreeningShouldThrowNotFound() {
        // given
        when(screeningDao.getById(1)).thenReturn(Optional.empty());
        // when & then
        assertThrows(NotFoundException.class, () -> screeningService.getScreening(1));
    }

    @Test
    void createScreeningShouldReturnDto() {
        // given
        CreateScreeningRequest req = mock(CreateScreeningRequest.class);
        Screening saved = mock(Screening.class);
        ScreeningDto dto = mock(ScreeningDto.class);
        when(screeningDao.createScreening(any(Screening.class))).thenReturn(saved);
        when(mapper.map(saved)).thenReturn(dto);
        // when
        ScreeningDto result = screeningService.createScreening(req);
        // then
        assertEquals(dto, result);
    }

    @Test
    void getScreeningDetailShouldReturnDto() {
        // given
        Screening screening = mock(Screening.class);
        ScreeningDto dto = mock(ScreeningDto.class);
        Room room = mock(Room.class);
        Seat seat = mock(Seat.class);
        Ticket ticket = mock(Ticket.class);
        when(screeningDao.getScreeningDetails(1)).thenReturn(Optional.of(screening));
        when(screening.getTickets()).thenReturn(List.of(ticket));
        when(ticket.getSeat()).thenReturn(seat);
        when(seat.getId()).thenReturn(5);
        when(screening.getRoom()).thenReturn(room);
        when(room.getSeats()).thenReturn(List.of(seat));
        when(seat.getId()).thenReturn(5);
        when(seat.withOccupied(true)).thenReturn(seat);
        when(room.withSeats(anyList())).thenReturn(room);
        when(screening.withRoom(room)).thenReturn(screening);
        when(mapper.map(screening)).thenReturn(dto);
        // when
        ScreeningDto result = screeningService.getScreeningDetail(1);
        // then
        assertEquals(dto, result);
    }

    @Test
    void getScreeningDetailShouldThrowNotFound() {
        // given
        when(screeningDao.getScreeningDetails(1)).thenReturn(Optional.empty());
        // when & then
        assertThrows(NotFoundException.class, () -> screeningService.getScreeningDetail(1));
    }
}

