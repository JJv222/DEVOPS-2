package com.piis.cinema.infrastructure.security;

import com.piis.cinema.business.dao.UserDAO;
import com.piis.cinema.domain.User;
import com.piis.cinema.infrastructure.database.mapper.UserEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository implements UserDAO {
    private final UserJpaRepository repository;
    private final UserEntityMapper mapper;


    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(mapper::map);
    }

    @Override
    public User updateUser(User user) {
        return mapper.map(repository.save(mapper.map(user)));
    }
}
