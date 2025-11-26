package com.piis.cinema.api.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class FilmDto {
    Integer id;
    String title;
    String description;
    String image;
    List<ScreeningDto> screenings;

}
