package com.piis.cinema.api.rest;

import com.piis.cinema.api.dto.ScreeningDto;
import com.piis.cinema.api.request.CreateScreeningRequest;
import com.piis.cinema.business.ScreeningService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "REST APIs for screening",
        description = "REST APIs in cinema service to CREATE, UPDATE AND GET screenings"
)
@RestController
@RequestMapping("/api/screening")
@RequiredArgsConstructor
public class ScreeningController {

    private final ScreeningService screeningService;

    @Operation(
            summary = "Get Screening details REST API",
            description = "Get screening details"
    )
    @GetMapping
    public ResponseEntity<ScreeningDto> getScreenings(
            @RequestParam Integer screeningId
    ) {
        return ResponseEntity.ok(screeningService.getScreeningDetail(screeningId));
    }

    @Operation(
            summary = "Get Screenings REST API",
            description = "Get all screening"
    )
    @PostMapping
    public ResponseEntity<ScreeningDto> createScreening( @RequestBody CreateScreeningRequest request ) {
        return ResponseEntity.ok(screeningService.createScreening(request));
    }
}
