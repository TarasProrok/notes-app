INSERT INTO access.users
(user_id,
 username,
 password,
 enabled,
 nickname,
 birthday,
 gender_id,
 created_date,
 updated_date,
 role
 )
 VALUES ('2D1EBC5B7D2741979CF0E84451C5AAA1', 'cat@doc.com', '$2a$12$nWQkBM6cLqjYaM6AOFTus.5c9d.GwmLatHHYU5.Uantl2OEa2WpSW', true, 'cat','2023-01-01', 2,'2023-01-01', '2023-01-01', 'ROLE_ADMIN'),
        ('2D1EBC5B7D2741979CF0E84451C5AAA2', 'dog@doc.com', '$2a$12$nWQkBM6cLqjYaM6AOFTus.5c9d.GwmLatHHYU5.Uantl2OEa2WpSW', true, 'dog','2023-01-01', 1,'2023-01-01', '2023-01-01', 'ROLE_USER'),
        ('2D1EBC5B7D2741979CF0E84451C5AAA3', 'ant@doc.com', '$2a$12$nWQkBM6cLqjYaM6AOFTus.5c9d.GwmLatHHYU5.Uantl2OEa2WpSW', true, 'ant','2023-01-01', 1,'2023-01-01', '2023-01-01', 'ROLE_USER'),
        ('2D1EBC5B7D2741979CF0E84451C5AAA4', 'fox@doc.com', '$2a$12$nWQkBM6cLqjYaM6AOFTus.5c9d.GwmLatHHYU5.Uantl2OEa2WpSW', true, 'fox','2023-01-01', 2,'2023-01-01', '2023-01-01', 'ROLE_USER'),
        ('2D1EBC5B7D2741979CF0E84451C5AAA5', 'pig@doc.com', '$2a$12$nWQkBM6cLqjYaM6AOFTus.5c9d.GwmLatHHYU5.Uantl2OEa2WpSW', false,'dog','2023-01-01', 1,'2023-01-01', '2023-01-01', 'ROLE_USER');