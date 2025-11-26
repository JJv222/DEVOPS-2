package com.piis.cinema.api.dto.mapper;

import com.piis.cinema.api.dto.FilmDto;
import com.piis.cinema.domain.Film;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilmDtoMapper {
    private final ScreeningDtoMapper screeningDtoMapper;
    public Film map(FilmDto filmDto){
        return Film.builder()
                .id(filmDto.getId())
                .description(filmDto.getDescription())
                .image(filmDto.getImage())
                .title(filmDto.getTitle())
                .build();
    }

    public FilmDto map(Film film){
        return FilmDto.builder()
                .id(film.getId())
                .description(film.getDescription())
                .image(film.getImage())
                .screenings(film.getScreenings().stream().map(screeningDtoMapper::map).toList())
                .title(film.getTitle())
                .build();
    }
}
