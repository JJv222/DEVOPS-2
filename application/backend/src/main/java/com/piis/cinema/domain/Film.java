package com.piis.cinema.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.List;

@Value
@With
@Builder
public class Film {
    Integer id;
    String title;
    String description;
    Boolean isActive;
    String image;
    List<Screening> screenings;
}
