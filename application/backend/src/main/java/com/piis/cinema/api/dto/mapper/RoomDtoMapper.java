package com.piis.cinema.api.dto.mapper;


import com.piis.cinema.api.dto.RoomDto;
import com.piis.cinema.domain.Room;

public class RoomDtoMapper {
    public Room map(RoomDto filmDto){
        return Room.builder()
                .id(filmDto.getId())
                .name(filmDto.getName())
                .build();
    }

    public RoomDto map(Room film){
        return RoomDto.builder()
                .id(film.getId())
                .name(film.getName())
                .build();
    }
}
