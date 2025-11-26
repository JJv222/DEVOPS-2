package com.piis.cinema.infrastructure.database.repository.jpa;

import com.piis.cinema.infrastructure.database.entity.FilmEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FilmJpaRepository extends JpaRepository<FilmEntity, Integer> {

    @EntityGraph(value = "FilmEntity.screenings.room", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT DISTINCT f FROM FilmEntity f " +
            "JOIN FETCH  f.screenings s " +
            "WHERE f.isActive = true " +
            "AND s.dateTime BETWEEN :startOfDay AND :endOfDay")
    List<FilmEntity> getActiveFilmWithScreeningsByDate(@Param("startOfDay") LocalDateTime startOfDay,
                                                 @Param("endOfDay") LocalDateTime endOfDay);

    Optional<FilmEntity> findByTitle(String name);
}
