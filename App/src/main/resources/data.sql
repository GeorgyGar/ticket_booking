DELETE FROM ticket;
DELETE FROM route;
DELETE FROM carrier;
DELETE FROM app_user;

INSERT INTO carrier (carrier_name, phone) VALUES
('FastExpress', '+7-900-123-45-67'),
('GoTravel', '+7-901-765-43-21');

INSERT INTO route (origin, destination, carrier_id, time) VALUES
('Москва', 'Санкт-Петербург', 1, 240),
('Казань', 'Нижний Новгород', 2, 180);

-- пример для теста: пароль — `password123`

INSERT INTO app_user (user_login, password, full_name, role) VALUES
('admin', '$2a$10$yGFdg7ysYOeRbJLqaBVOi./pM8uhbncH1OaUuhkyl56ZxQ/y1JGVe', 'Администратор Системы', 'ADMIN'),
('user1', '$2a$10$yGFdg7ysYOeRbJLqaBVOi./pM8uhbncH1OaUuhkyl56ZxQ/y1JGVe', 'Покупатель Иванов', 'BUYER');

INSERT INTO ticket (route_id, date_time, seat_number, price, purchased, user_id) VALUES
(1, '2025-08-01 08:00:00', 1, 1500.00, false, NULL),
(1, '2025-08-01 08:00:00', 2, 1500.00, false, NULL),
(1, '2025-08-01 08:00:00', 3, 1500.00, false, NULL),
(2, '2025-08-02 12:00:00', 1, 1200.00, false, NULL),
(2, '2025-08-02 12:00:00', 2, 1200.00, false, NULL);
