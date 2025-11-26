package com.piis.cinema.business;

import com.piis.cinema.api.dto.ScreeningDto;
import com.piis.cinema.api.dto.mapper.ScreeningDtoMapper;
import com.piis.cinema.api.request.CreateScreeningRequest;
import com.piis.cinema.business.dao.ScreeningDao;
import com.piis.cinema.domain.Exception.NotFoundException;
import com.piis.cinema.domain.Film;
import com.piis.cinema.domain.Room;
import com.piis.cinema.domain.Screening;
import com.piis.cinema.domain.Seat;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningDao screeningDao;
    private final ScreeningDtoMapper mapper;

    @Transactional
    public Page<ScreeningDto> getScreenings(Integer pageNumber, Integer pageSize) {
        Sort sort = Sort.by(
                Sort.Order.asc("dateTime")
        );
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return screeningDao.find(pageable).map(mapper::map);
    }

    @Transactional
    public Screening getScreening(Integer id) {
        return screeningDao.getById(id)
                .orElseThrow(() -> new NotFoundException("Screening with id do not exist"));
    }

    @Transactional
    public ScreeningDto createScreening(CreateScreeningRequest request) {
        return mapper.map(screeningDao.createScreening(buildScreening(request)));
    }

    @Transactional
    public ScreeningDto getScreeningDetail(Integer screeningId) {
        Screening screening = screeningDao.getScreeningDetails(screeningId)
                .orElseThrow(() -> new NotFoundException("Screening with id " + screeningId + " does not exist"));

        List<Integer> takenSeatIds = screening.getTickets().stream()
                .map(ticket -> ticket.getSeat().getId())
                .toList();

        List<Seat> updatedSeats = screening.getRoom().getSeats().stream()
                .map(seat -> takenSeatIds.contains(seat.getId()) ? seat.withOccupied(true) : seat)
                .toList();

        Room updatedRoom = screening.getRoom().withSeats(updatedSeats);
        Screening updatedScreening = screening.withRoom(updatedRoom);

        return mapper.map(updatedScreening);
    }

    private Screening buildScreening(CreateScreeningRequest request) {
        return Screening.builder()
                .durationMinutes(request.duration())
                .film(
                        Film.builder()
                                .id(request.filmId())
                                .build())
                .room(
                        Room.builder()
                                .id(request.RoomId())
                                .build())
                .dateTime(request.dateTime())
                .build();
    }
}
