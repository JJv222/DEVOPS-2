package com.piis.cinema.api.rest;

import com.piis.cinema.api.dto.TicketDto;
import com.piis.cinema.api.request.CreateTicketRequest;
import com.piis.cinema.business.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "REST APIs for ticket",
        description = "REST APIs in cinema service to CREATE, UPDATE AND GET ticket"
)
@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @Operation(
            summary = "Create ticket REST API",
            description = "Create ticket for screening"
    )
    @PostMapping
    public ResponseEntity<TicketDto> createTicket(@RequestBody CreateTicketRequest request){
        return ResponseEntity.ok(ticketService.createTicket(request));
    }

    @Operation(
            summary = "Create tickets REST API",
            description = "Create multiple tickets for  specified screening"
    )
    @PostMapping("/bulk")
    public ResponseEntity<List<TicketDto>> createTicketBulk(@RequestBody List<CreateTicketRequest> bulkRequest){
        return ResponseEntity.ok(ticketService.createTicketBulk(bulkRequest));
    }

    @Operation(
            summary = "Update ticket REST API",
            description = "Update ticket status"
    )
    @PatchMapping
    public ResponseEntity<TicketDto> updateStatus(@RequestParam Integer ticketId){
        return ResponseEntity.ok(ticketService.updateStatus(ticketId));
    }

    @Operation(
            summary = "Get ticket REST API",
            description = "Get all tickets for screening"
    )
    @GetMapping
    public ResponseEntity<List<TicketDto>> getTicketsForScreening(@RequestParam Integer screeningId){
        return ResponseEntity.ok(ticketService.getTicketsForScreening(screeningId));
    }

    @Operation(
            summary = "Get ticket REST API",
            description = "Get ticket by reservationCode"
    )
    @GetMapping("/code")
    public ResponseEntity<TicketDto> getTicketByCode(@RequestParam String code){
        return ResponseEntity.ok(ticketService.getTicketByCode(code));
    }

}
