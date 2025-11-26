package com.piis.cinema.infrastructure.database.mapper;

import com.piis.cinema.domain.Film;
import com.piis.cinema.infrastructure.database.entity.FilmEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class FilmEntityMapper {
    private final ScreeningEntityMapper screeningEntityMapper;

    public Film map(FilmEntity filmEntity) {
        return Film.builder()
                .id(filmEntity.getId())
                .image(filmEntity.getImage())
                .screenings(
                        Hibernate.isInitialized(filmEntity.getScreenings())
                                ? filmEntity.getScreenings().stream().map(screeningEntityMapper::map).toList()
                                : List.of()
                )
                .description(filmEntity.getDescription())
                .title(filmEntity.getTitle())
                .build();
    }

    public FilmEntity map(Film film) {
        return FilmEntity.builder()
                .id(film.getId())
                .description(film.getDescription())
                .screenings(Set.of())
                .image(film.getImage())
                .title(film.getTitle())
                .isActive(film.getIsActive())
                .build();
    }


}
