package com.piis.cinema.business;

import com.piis.cinema.api.dto.FilmDto;
import com.piis.cinema.api.dto.mapper.FilmDtoMapper;
import com.piis.cinema.api.request.CreateFilmRequest;
import com.piis.cinema.business.dao.FilmDao;
import com.piis.cinema.domain.Exception.NotFoundException;
import com.piis.cinema.domain.Exception.ObjectAlreadyExistException;
import com.piis.cinema.domain.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class FilmServiceTest {
    @Mock
    private FilmDao filmDao;
    @Mock
    private FilmDtoMapper mapper;
    @Mock
    private MultipartFile multipartFile;
    @InjectMocks
    private FilmService filmService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filmService = new FilmService(filmDao, mapper);
    }

    @Test
    void createShouldThrowExceptionWhenTitleExists() {
        // given
        CreateFilmRequest request = mock(CreateFilmRequest.class);
        when(request.title()).thenReturn("Test");
        when(filmDao.findByTitle("Test")).thenReturn(Optional.of(mock(Film.class)));
        // when & then
        assertThrows(ObjectAlreadyExistException.class, () -> filmService.create(request, multipartFile));
    }

    @Test
    void removeShouldThrowExceptionWhenFilmNotFound() {
        // given
        when(filmDao.findById(1)).thenReturn(Optional.empty());
        // when & then
        assertThrows(NotFoundException.class, () -> filmService.remove(1));
    }

    @Test
    void removeShouldDeleteFilmWhenFilmExists() {
        // given
        Film film = mock(Film.class);
        FilmDto filmDto = mock(FilmDto.class);
        when(filmDao.findById(1)).thenReturn(Optional.of(film));
        when(mapper.map(film)).thenReturn(filmDto);
        // when
        FilmDto result = filmService.remove(1);
        // then
        verify(filmDao).deleteFilm(film);
        assertEquals(filmDto, result);
    }

    @Test
    void findAllShouldReturnPageOfFilmDto() {
        // given
        Film film = mock(Film.class);
        FilmDto filmDto = mock(FilmDto.class);
        Page<Film> page = new PageImpl<>(List.of(film));
        when(filmDao.findAll(any(Pageable.class))).thenReturn(page);
        when(mapper.map(film)).thenReturn(filmDto);
        // when
        Page<FilmDto> result = filmService.findAll(0, 10);
        //then
        assertEquals(1, result.getTotalElements());
        assertEquals(filmDto, result.getContent().getFirst());
    }

}
