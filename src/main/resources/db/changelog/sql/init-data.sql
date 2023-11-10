INSERT INTO premium_limit(id, max_total_premium)
VALUES (1, 100000);

INSERT INTO insurance(id, name, factor_insurance_volume, factor_insurance_bonus)
VALUES
(1, 'Not', 0, 0),
(2, 'Life', 1, 1.4),
(3, 'Unemployment', 1, 1.6),
(4, 'Combo', 2, 3);

INSERT INTO product_group(id, name, factor_premium, min_premium, max_premium, type_credit, min_amount_for_calculating_credit_premium)
VALUES
(1, 'Household appliances and electronics', 0.002, 30, 3000, 'POINT_OF_SALE', 0),
(2, 'Furniture, furs and clothing', 0.0009, 30, 3000, 'POINT_OF_SALE', 0),
(3, 'Mobile phones and jewelry', 0.0006, 30, 1300, 'POINT_OF_SALE', 0),
(4, 'General motivation', 0.0013, 30, 3000, 'POINT_OF_SALE', 0),
(5, 'Construction materials and motorcycles', 0.0013, 30, 3000, 'POINT_OF_SALE', 0),
(6, 'Services', 0.0013, 30, 3000, 'POINT_OF_SALE', 0),
(7, 'Cash on card type 1 to 3', 0.006, 0, 6000, 'CASH_ON_CARD', 100000),
(8, 'Cash on card type 4', 0.006, 0, 6000, 'CASH_ON_CARD', 100000);

INSERT INTO criteria_bonus_for_fur(id, min_sum, min_sms, bonus)
VALUES (1, 750000, 70, 7500);

INSERT INTO productivity_level(id, name, premium, min_count_credits, min_sum_amount_credits, min_penetration_sms, min_penetration_insurance)
VALUES
(1, 'Level 0', 0, 0, 0, 0, 0),
(2, 'Copper', 7000, 16, 500000, 75, 0),
(3, 'Bronze', 10000, 21, 650000, 85, 30),
(4, 'Silver', 15000, 30, 1000000, 90, 40),
(5, 'Gold', 20000, 36, 1500000, 95, 60);