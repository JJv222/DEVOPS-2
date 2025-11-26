package com.piis.cinema.business;


import com.piis.cinema.api.dto.FilmDto;
import com.piis.cinema.api.dto.mapper.FilmDtoMapper;
import com.piis.cinema.api.request.CreateFilmRequest;
import com.piis.cinema.business.dao.FilmDao;
import com.piis.cinema.domain.Exception.NotFoundException;
import com.piis.cinema.domain.Exception.ObjectAlreadyExistException;
import com.piis.cinema.domain.Film;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmDao filmDao;
    private final FilmDtoMapper mapper;

    @Transactional
    public Page<FilmDto> findAll(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return filmDao.findAll(pageable).map(mapper::map);
    }

    @Transactional
    public FilmDto create(CreateFilmRequest request, MultipartFile image) {
        filmDao.findByTitle(request.title())
                .ifPresent(f -> { throw new ObjectAlreadyExistException("Film with this title already exists!"); });
        Film film = Film.builder()
                .isActive(true)
                .title(request.title())
                .screenings(List.of())
                .description(request.description())
                .image(createFile(image))
                .build();
        return mapper.map(filmDao.createFilm(film));
    }

    public String createFile(MultipartFile file) {
        String fileName = PhotoNumberGenerator.generatePhotoNumber(OffsetDateTime.now());
        try {
            Path uploadPath = new ClassPathResource("static/images/").getFile().toPath();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            log.error("File upload failed for file: {}", fileName, e);
            return "blank.png";
        }
    }

    @Transactional
    public FilmDto remove(Integer filmId) {
        Film film = filmDao.findById(filmId)
                .orElseThrow(() -> new NotFoundException("Film with this id does not exist!"));
        filmDao.deleteFilm(film);
        return mapper.map(film);
    }

    @Transactional
    public List<FilmDto> getActiveFilmWithScreeningsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return filmDao.getActiveFilmWithScreeningsByDate(startOfDay, endOfDay)
                .stream()
                .map(mapper::map)
                .toList();
    }
}
