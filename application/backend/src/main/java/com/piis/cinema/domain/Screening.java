package com.piis.cinema.domain;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.time.LocalDateTime;
import java.util.List;

@Data
@With
@Builder
public class Screening {
    private Integer id;
    private LocalDateTime dateTime;
    private Film film;
    private Room room;
    private int durationMinutes;
    private List<Ticket> tickets;
}
