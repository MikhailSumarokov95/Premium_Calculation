INSERT INTO premium_limit(id, max_total_premium)
VALUES (1, 100000);

INSERT INTO insurance(id, name, factor_insurance_volume, factor_insurance_bonus)
VALUES
(1, 'Нет', 0, 0),
(2, 'Жизнь', 100, 1.4),
(3, 'Безработица', 100, 1.6),
(4, 'Комбо', 200, 3);

INSERT INTO product_group(id, name, factor_premium, min_premium, max_premium, type_credit, min_amount_for_calculating_credit_premium)
VALUES
(1, 'Бытовая техника и электроника', 0.002, 30, 3000, 'POINT_OF_SALE', 0),
(2, 'Мебель, Меха и одежда', 0.0009, 30, 3000, 'POINT_OF_SALE', 0),
(3, 'Мобильные телефоны и Ювелирные изделия', 0.0006, 30, 1300, 'POINT_OF_SALE', 0),
(4, 'Общая мотивация', 0.0013, 30, 3000, 'POINT_OF_SALE', 0),
(5, 'Стройматериалы и Мототехника', 0.0013, 30, 3000, 'POINT_OF_SALE', 0),
(6, 'Услуги', 0.0013, 30, 3000, 'POINT_OF_SALE', 0),
(7, 'Наличные на карту тип с 1 по 3', 0.006, 0, 6000, 'CASH_ON_CARD', 100000),
(8, 'Наличные на карту тип 4', 0.006, 0, 6000, 'CASH_ON_CARD', 100000);

INSERT INTO criteria_bonus_for_fur(id, min_sum, min_sms, bonus)
VALUES (1, 750000, 70, 7500);

INSERT INTO productivity_level(id, name, premium, min_count_credits, min_sum_amount_credits, min_penetration_sms, min_penetration_insurance)
VALUES
(1, 'Уровень 0', 0, 0, 0, 0, 0),
(2, 'Медный', 7000, 16, 500000, 75, 0),
(3, 'Бронзовый', 10000, 21, 650000, 85, 30),
(4, 'Серебрянный', 15000, 30, 1000000, 90, 40),
(5, 'Золотой', 20000, 36, 1500000, 95, 60);