package com.piis.cinema.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "Create ticket request",
        description = "Schema to hold ticket information"
)
public record CreateTicketRequest(
        @Schema(
                description = "Screening id",
                example = "1"
        )
        Integer screeningId,
        @Schema(
                description = "Seat id in screening room",
                example = "1"
        )
        Integer seatId
) {}
