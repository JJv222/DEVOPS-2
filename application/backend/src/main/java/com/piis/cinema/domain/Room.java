package com.piis.cinema.domain;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.util.List;

@Data
@With
@Builder
public class Room {
    private Integer id;
    private String name;
    private List<Seat> seats;
}
