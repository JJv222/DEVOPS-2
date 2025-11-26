CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,
    screening_id INT NOT NULL,
    seat_id INT,
    reservation_code VARCHAR(64) UNIQUE NOT NULL,
    status VARCHAR(20),
    FOREIGN KEY (screening_id) REFERENCES screening(id),
    FOREIGN KEY (seat_id) REFERENCES seat(id),
    UNIQUE (screening_id, seat_id)
);
