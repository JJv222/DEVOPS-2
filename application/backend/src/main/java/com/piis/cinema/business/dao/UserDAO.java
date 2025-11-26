package com.piis.cinema.business.dao;


import com.piis.cinema.domain.User;

import java.util.Optional;

public interface UserDAO {
    Optional<User> findByEmail(String email);

    User updateUser(User user);
}
