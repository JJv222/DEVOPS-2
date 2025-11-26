package com.piis.cinema.infrastructure.database.repository;

import com.piis.cinema.business.dao.FilmDao;
import com.piis.cinema.domain.Film;
import com.piis.cinema.infrastructure.database.mapper.FilmEntityMapper;
import com.piis.cinema.infrastructure.database.repository.jpa.FilmJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FilmRepository implements FilmDao {

    private final FilmJpaRepository repository;
    private final FilmEntityMapper mapper;


    @Override
    public Film createFilm(Film film) {
        return mapper.map(repository.save(mapper.map(film)));
    }

    @Override
    public void deleteFilm(Film film) {
        repository.delete(mapper.map(film));
    }

    @Override
    public Page<Film> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::map);
    }

    @Override
    public Optional<Film> findById(Integer filmId) {
        return repository.findById(filmId).map(mapper::map);
    }

    @Override
    public Optional<Film> findByTitle(String tittle) {
        return repository.findByTitle(tittle).map(mapper::map);
    }

    @Override
    public List<Film> getActiveFilmWithScreeningsByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.getActiveFilmWithScreeningsByDate(startDate, endDate).stream().map(mapper::map).toList();
    }
}
