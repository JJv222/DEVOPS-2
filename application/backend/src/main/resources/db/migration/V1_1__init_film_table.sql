CREATE TABLE film (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    is_active BOOLEAN NOT NULL,
    image VARCHAR(256) NOT NULL,
    description VARCHAR(255)
);
