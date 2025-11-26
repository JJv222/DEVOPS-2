package com.piis.cinema.business;

import com.piis.cinema.business.dao.SeatDao;
import com.piis.cinema.domain.Exception.NotFoundException;
import com.piis.cinema.domain.Seat;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatDao seatDao;

    @Transactional
    public Seat getSeatById(Integer id) {
        return seatDao.getSeat(id)
                .orElseThrow(() -> new NotFoundException("Seat with this id do not exist!"));
    }
}

