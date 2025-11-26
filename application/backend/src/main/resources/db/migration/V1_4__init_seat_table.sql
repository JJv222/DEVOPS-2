CREATE TABLE seat (
    id SERIAL PRIMARY KEY,
    room_id INT NOT NULL,
    row_number INT NOT NULL,
    seat_number INT NOT NULL,
    FOREIGN KEY (room_id) REFERENCES room(id),
    UNIQUE (room_id, row_number, seat_number)
);
