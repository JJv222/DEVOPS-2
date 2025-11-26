package com.piis.cinema.business.dao;

import com.piis.cinema.domain.Seat;

import java.util.Optional;

public interface SeatDao {
    Optional<Seat> getSeat(Integer id);
}
