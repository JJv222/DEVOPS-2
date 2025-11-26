package com.piis.cinema.business.dao;

import com.piis.cinema.domain.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FilmDao {
    Film createFilm(Film film);
    void deleteFilm(Film film);
    Page<Film> findAll(Pageable pageable);
    Optional<Film> findById(Integer filmId);
    Optional<Film> findByTitle(String tittle);
    List<Film> getActiveFilmWithScreeningsByDate(LocalDateTime startDate, LocalDateTime endDate);
}
