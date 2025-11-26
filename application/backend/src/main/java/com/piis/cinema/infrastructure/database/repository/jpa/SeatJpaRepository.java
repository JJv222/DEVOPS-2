package com.piis.cinema.infrastructure.database.repository.jpa;

import com.piis.cinema.infrastructure.database.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatJpaRepository extends JpaRepository<SeatEntity,Integer> {
}
