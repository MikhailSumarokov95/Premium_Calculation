DROP TABLE IF EXISTS credit, preliminary_credit_result, efficiency, insured, product_group CASCADE;

CREATE TABLE IF NOT EXISTS insurance(
    id SERIAL PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    factor_insurance_volume DOUBLE PRECISION NOT NULL,
    factor_insurance_bonus DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS product_group(
    id SERIAL PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    factor_premium DOUBLE PRECISION NOT NULL,
    min_premium DOUBLE PRECISION NOT NULL,
    max_premium DOUBLE PRECISION NOT NULL,
    is_coc BOOLEAN NOT NULL DEFAULT FALSE,
    min_amount_for_calculating_credit_premium DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS credit(
    id SERIAL PRIMARY KEY,
    product_group_id INTEGER NOT NULL REFERENCES product_group(id),
    amount DOUBLE PRECISION NOT NULL,
    term INTEGER NOT NULL,
    rate DOUBLE PRECISION NOT NULL,
    insurance_id INTEGER NOT NULL REFERENCES insurance(id),
    is_connected_sms BOOLEAN NOT NULL DEFAULT FALSE,
    is_fur BOOLEAN NOT NULL DEFAULT FALSE,
    is_consultant_availability BOOLEAN NOT NULL DEFAULT FALSE,
    is_used_self_reject BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS preliminary_credit_result(
    credit_id INT PRIMARY KEY REFERENCES credit(id),
    premium DOUBLE PRECISION NOT NULL,
    credit_total DOUBLE PRECISION NOT NULL,
    insurance_bonus DOUBLE PRECISION NOT NULL,
    insurance_volume DOUBLE PRECISION NOT NULL,
    credit_previously DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS efficiency(
    productivity_level VARCHAR(64) NOT NULL,
    total_premium DOUBLE PRECISION NOT NULL,
    premium_for_credits DOUBLE PRECISION NOT NULL,
    fur_bonus DOUBLE PRECISION NOT NULL,
    total_productivity DOUBLE PRECISION NOT NULL,
    premium_insurance DOUBLE PRECISION NOT NULL,
    premium_for_additional_products DOUBLE PRECISION NOT NULL
);