DROP TABLE IF EXISTS ticket, route, carrier, app_user CASCADE;

-- Таблица перевозчиков
CREATE TABLE IF NOT EXISTS carrier (
    id SERIAL PRIMARY KEY,
    carrier_name VARCHAR(255) NOT NULL,
    phone VARCHAR(20)
);

-- Таблица маршрутов
CREATE TABLE IF NOT EXISTS route (
    id SERIAL PRIMARY KEY,
    origin VARCHAR(255) NOT NULL,
    destination VARCHAR(255) NOT NULL,
    carrier_id BIGINT REFERENCES carrier(id) ON DELETE CASCADE,
    time INT NOT NULL
);

-- Таблица пользователей
CREATE TABLE IF NOT EXISTS app_user (
    id SERIAL PRIMARY KEY,
    user_login VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- Таблица билетов
CREATE TABLE IF NOT EXISTS ticket (
    id SERIAL PRIMARY KEY,
    route_id BIGINT REFERENCES route(id) ON DELETE CASCADE,
    date_time TIMESTAMP NOT NULL,
    seat_number SMALLINT NOT NULL,
    price FLOAT NOT NULL,
    user_id BIGINT REFERENCES app_user(id),
    purchased BOOLEAN DEFAULT FALSE
);
