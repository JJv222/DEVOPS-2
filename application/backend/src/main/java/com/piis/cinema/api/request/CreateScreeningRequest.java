package com.piis.cinema.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(
        name = "Create screening request",
        description = "Schema to hold screening information"
)
public record CreateScreeningRequest(
        @Schema(
                description = "Room id",
                example = "1"
        )
        Integer RoomId,
        @Schema(
                description = "Seat id in screening room",
                example = "1"
        )
        Integer filmId,
        @Schema(
                description = "Screening duration",
                example = "120"
        )
        Integer duration,
        @Schema(
                description = "Screening date",
                example = "25-05-20T18:00:00"
        )
        LocalDateTime dateTime
) {}
