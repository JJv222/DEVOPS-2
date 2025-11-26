package com.piis.cinema.infrastructure.database.mapper;

import com.piis.cinema.domain.Room;
import com.piis.cinema.infrastructure.database.entity.RoomEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomEntityMapper {
    private final SeatEntityMapper seatEntityMapper;
    public Room map(RoomEntity entity) {
        return Room.builder()
                .id(entity.getId())
                .seats(
                        Hibernate.isInitialized(entity.getSeats())
                                ? entity.getSeats().stream().map(seatEntityMapper::map).toList()
                                : List.of()
                )
                .name(entity.getName())
                .build();
    }

    public RoomEntity map(Room user) {
        return RoomEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
