package com.piis.cinema.api.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoomDto {
    Integer id;
    String name;
}
