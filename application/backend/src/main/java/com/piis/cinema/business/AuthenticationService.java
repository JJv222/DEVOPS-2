package com.piis.cinema.business;

import com.piis.cinema.api.auth.AuthenticationRequest;
import com.piis.cinema.api.auth.AuthenticationResponse;
import com.piis.cinema.api.auth.RegisterRequest;
import com.piis.cinema.domain.Exception.ObjectAlreadyExistException;
import com.piis.cinema.infrastructure.security.JwtService;
import com.piis.cinema.infrastructure.security.Role;
import com.piis.cinema.infrastructure.security.UserEntity;
import com.piis.cinema.infrastructure.security.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserJpaRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        repository.findByEmail(request.getEmail())
                .ifPresent(u -> { throw new ObjectAlreadyExistException("User with this email already exist!"); });
        UserEntity user = buildUser(request);
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole())
                .email(user.getEmail())
                .build();
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        UserEntity user = repository.findByEmail(request.getEmail())
                .filter(UserEntity::getActive)
                .orElseThrow(() -> new BadCredentialsException("Cannot find user with this email or user is not active"));
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole())
                .email(user.getEmail())
                .build();
    }

    private UserEntity buildUser(RegisterRequest request) {
        return UserEntity.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .phone(request.getPhone())
                .email(request.getEmail())
                .active(true)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();
    }

}
