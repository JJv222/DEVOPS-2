package com.piis.cinema.domain;

import com.piis.cinema.infrastructure.security.Role;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

@Value
@With
@Builder
@EqualsAndHashCode
public class User {
    Integer id;
    String name;
    String surname;
    String phone;
    String email;
    Boolean active;
    Role role;
    String password;
}
