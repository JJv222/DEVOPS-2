package com.piis.cinema.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "Create film request",
        description = "Schema to hold film information"
)
public record CreateFilmRequest(
        @Schema(
                description = "Film tittle",
                example = "Harry Potter and the Philosopher's Stone"
        )
        String title,
        @Schema(
                description = "Film description",
                example = "An orphaned boy discovers he is a wizard and attends a magical school, where he uncovers the truth about his past and faces a dark force threatening the world."
        )
        String description
) {}
