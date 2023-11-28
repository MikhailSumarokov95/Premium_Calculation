INSERT INTO premium_limit(max_total_premium, is_actual)
VALUES (100000, 'true');

INSERT INTO insurance(name, factor_insurance_volume, factor_insurance_bonus)
VALUES
('Нет', 0, 0),
('Жизнь', 100, 1.6),
('Безработица', 100, 1.4),
('Комбо', 200, 3);

INSERT INTO product_group(name, factor_premium, min_premium, max_premium, type_credit, min_amount_for_calculating_credit_premium)
VALUES
('Бытовая техника и электроника', 0.2, 30, 3000, 'POINT_OF_SALE', 0),
('Мебель, Меха и одежда', 0.09, 30, 3000, 'POINT_OF_SALE', 0),
('Мобильные телефоны и Ювелирные изделия', 0.06, 30, 1300, 'POINT_OF_SALE', 0),
('Общая мотивация', 0.13, 30, 3000, 'POINT_OF_SALE', 0),
('Стройматериалы и Мототехника', 0.13, 30, 3000, 'POINT_OF_SALE', 0),
('Услуги', 0.13, 30, 3000, 'POINT_OF_SALE', 0),
('Наличные на карту тип с 1 по 3', 0.6, 0, 6000, 'CASH_ON_CARD', 100000),
('Наличные на карту тип 4', 0.6, 0, 6000, 'CASH_ON_CARD', 100000);

INSERT INTO criteria_bonus_for_fur( min_sum, min_sms, bonus)
VALUES (750000, 70, 7500);

INSERT INTO productivity_level(name, premium, min_count_credits, min_sum_amount_credits, min_penetration_sms, min_penetration_insurance, is_default)
VALUES
('Уровень 0', 0, 0, 0, 0, 0, true),
('Медный', 7000, 16, 500000, 75, 0, false),
('Бронзовый', 10000, 21, 650000, 85, 30, false),
('Серебрянный', 15000, 30, 1000000, 90, 40, false),
('Золотой', 20000, 36, 1500000, 95, 60, false);

INSERT INTO users(username, password, email, role)
VALUES
('user', '$2a$10$WRxWgZREhg2X1cXDdJazEOoO06oztdpuqAAX8MHPl5VA2FSh8otu6', 'user@mail.ru', 'ROLE_CREDIT_SPECIALIST'),
('admin', '$2a$10$UQQgzIxNnHIrHX/LepyrDuOq3PhWNf8gUBUrFg2y5/td/GeY8tiSe', 'admin@mail.ru', 'ROLE_ADMIN'),
('myadmin', '$2a$10$jIg/x8OAgmkfdPwJPHKdWumbYNtJW41wfvjeW6phLue/kxXV78SNa', 'myadmin@mail.ru', 'ROLE_ADMIN');
