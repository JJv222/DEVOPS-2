package com.piis.cinema.infrastructure.database.repository;

import com.piis.cinema.business.dao.SeatDao;
import com.piis.cinema.domain.Seat;
import com.piis.cinema.infrastructure.database.mapper.SeatEntityMapper;
import com.piis.cinema.infrastructure.database.repository.jpa.SeatJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SeatRepository implements SeatDao {
    private final SeatJpaRepository seatJpaRepository;
    private final SeatEntityMapper mapper;

    @Override
    public Optional<Seat> getSeat(Integer id) {
        return seatJpaRepository.findById(id).map(mapper::map);
    }
}
