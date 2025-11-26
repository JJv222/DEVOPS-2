package com.piis.cinema.api.rest;


import com.piis.cinema.api.dto.FilmDto;
import com.piis.cinema.api.request.CreateFilmRequest;
import com.piis.cinema.business.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@Tag(
        name = "REST APIs for film",
        description = "REST APIs in cinema service to CREATE, UPDATE AND GET film"
)
@RestController
@RequestMapping("/api/film")
@AllArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @Operation(
            summary = "Get all film REST API",
            description = "Get all currently screenings films"
    )
    @GetMapping
    public ResponseEntity<Page<FilmDto>> getFilms(Integer pageNumber, Integer pageSize) {
        return ResponseEntity.ok(filmService.findAll(pageNumber, pageSize));
    }

    @Operation(
            summary = "Create film REST API",
            description = "Add new film"
    )
    @PostMapping
    public ResponseEntity<FilmDto> createFilm(
            @RequestPart("film") CreateFilmRequest film,
            @RequestPart("image") MultipartFile image
    ) {
        return ResponseEntity.ok(filmService.create(film, image));
    }

    @Operation(
            summary = "remove film REST API",
            description = "Remove film by id"
    )
    @DeleteMapping
    public ResponseEntity<FilmDto> removeFilm(
            @Parameter(description = "The unique identifier of the film", example = "1")
            @RequestParam Integer filmId
    ) {
        return ResponseEntity.ok(filmService.remove(filmId));
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage(@RequestParam String image) throws IOException {
        Resource resource = new ClassPathResource("/static/images/" + image);
        byte[] imageBytes = Files.readAllBytes(Path.of(resource.getURI()));
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
    }

    @GetMapping("/repertoire")
    public ResponseEntity<List<FilmDto>> getActiveFilmsWithScreenings(
            @Parameter(description = "The unique identifier of the film", example = "1")
            @RequestParam LocalDate date
    ) {
             return ResponseEntity.ok(filmService.getActiveFilmWithScreeningsByDate(date));
    }

}
