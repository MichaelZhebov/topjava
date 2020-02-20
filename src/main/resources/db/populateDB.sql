DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (id, user_id, date_time, description, calories) VALUES
(1, 100000, '2020-02-20 07:30','breakfast', 500),
(2, 100000, '2020-02-20 14:30','lunch', 1000),
(3, 100000, '2020-02-20 19:00','dinner', 1500),
(4, 100001, '2020-02-22 10:0','breakfast', 350),
(5, 100001, '2020-02-22 13:25','lunch', 800),
(6, 100001, '2020-02-22 20:30','dinner', 500),
(7, 100000, '2020-02-24 07:30','breakfast', 500),
(8, 100000, '2020-02-24 21:30','diner', 1000);