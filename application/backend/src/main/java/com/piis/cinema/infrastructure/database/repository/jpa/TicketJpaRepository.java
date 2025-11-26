package com.piis.cinema.infrastructure.database.repository.jpa;

import com.piis.cinema.infrastructure.database.entity.ScreeningEntity;
import com.piis.cinema.infrastructure.database.entity.SeatEntity;
import com.piis.cinema.infrastructure.database.entity.TicketEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketJpaRepository extends JpaRepository<TicketEntity, Integer> {
    @EntityGraph(value = "TicketEntity.screening.film", type = EntityGraph.EntityGraphType.FETCH)
    List<TicketEntity> findAllByScreening(ScreeningEntity screeningEntity);

    @EntityGraph(value = "TicketEntity.screening.film", type = EntityGraph.EntityGraphType.FETCH)
    Optional<TicketEntity> findByReservationCode(String code);

    Optional<TicketEntity> findByScreeningAndSeat(ScreeningEntity screening, SeatEntity seat);
}
