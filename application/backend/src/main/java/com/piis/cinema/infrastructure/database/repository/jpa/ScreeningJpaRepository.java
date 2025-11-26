package com.piis.cinema.infrastructure.database.repository.jpa;

import com.piis.cinema.infrastructure.database.entity.ScreeningEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface ScreeningJpaRepository extends JpaRepository<ScreeningEntity,Integer> {
    @Override
    @NonNull
    @EntityGraph(value = "screenings.room", type = EntityGraph.EntityGraphType.FETCH)
    Page<ScreeningEntity> findAll(@NonNull Pageable pageable);

    @EntityGraph(value = "screenings.details", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT s FROM screening s WHERE s.id = :id")
    Optional<ScreeningEntity> findByIdWithDetails(@Param("id") Integer id);
}
