package com.piis.cinema.business.dao;

import com.piis.cinema.domain.Screening;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ScreeningDao {
    Page<Screening> find(Pageable pageable);
    Optional<Screening> getById(Integer id);
    Screening createScreening(Screening screening);

    Optional<Screening> getScreeningDetails(Integer screeningId);
}
