package com.piis.cinema.infrastructure.database.repository;

import com.piis.cinema.business.dao.ScreeningDao;
import com.piis.cinema.domain.Screening;
import com.piis.cinema.infrastructure.database.mapper.ScreeningEntityMapper;
import com.piis.cinema.infrastructure.database.repository.jpa.ScreeningJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScreeningRepository implements ScreeningDao {

    private final ScreeningJpaRepository repository;
    private final ScreeningEntityMapper mapper;

    @Override
    public Page<Screening> find(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::map);
    }

    @Override
    public Optional<Screening> getScreeningDetails(Integer screeningId) {
        return repository.findByIdWithDetails(screeningId).map(mapper::map);
    }

    @Override
    public Optional<Screening> getById(Integer id) {
        return repository.findById(id).map(mapper::map);
    }

    @Override
    public Screening createScreening(Screening screening) {
        return mapper.map(repository.save(mapper.map(screening)));
    }
}
