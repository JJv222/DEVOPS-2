CREATE TABLE screening (
    id SERIAL PRIMARY KEY,
    film_id INT NOT NULL,
    room_id INT NOT NULL,
    date_time TIMESTAMP,
    duration_minutes INT NOT NULL,
    FOREIGN KEY (film_id) REFERENCES film(id),
    FOREIGN KEY (room_id) REFERENCES room(id)
);

