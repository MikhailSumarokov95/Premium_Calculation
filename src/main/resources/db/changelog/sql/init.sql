DROP TABLE IF EXISTS credit, preliminary_credit_result, efficiency, insured, product_group CASCADE;

CREATE TABLE IF NOT EXISTS insurance(
    id SERIAL PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    factor_insurance_volume NUMERIC NOT NULL,
    factor_insurance_bonus NUMERIC NOT NULL
);

CREATE TABLE IF NOT EXISTS product_group(
    id SERIAL PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    factor_premium NUMERIC NOT NULL,
    min_premium NUMERIC NOT NULL,
    max_premium NUMERIC NOT NULL,
    is_coc BOOLEAN NOT NULL DEFAULT FALSE,
    min_amount_for_calculating_credit_premium NUMERIC NOT NULL
);

CREATE TABLE IF NOT EXISTS credit(
    id SERIAL PRIMARY KEY,
    product_group_id INTEGER NOT NULL REFERENCES product_group(id),
    amount NUMERIC NOT NULL,
    term INTEGER NOT NULL,
    rate NUMERIC NOT NULL,
    insurance_id INTEGER NOT NULL REFERENCES insurance(id),
    is_connected_sms BOOLEAN NOT NULL DEFAULT FALSE,
    is_fur BOOLEAN NOT NULL DEFAULT FALSE,
    is_consultant_availability BOOLEAN NOT NULL DEFAULT FALSE,
    is_used_self_reject BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS preliminary_credit_result(
    credit_id SERIAL PRIMARY KEY REFERENCES credit(id),
    premium NUMERIC NOT NULL,
    credit_total NUMERIC NOT NULL,
    insurance_bonus NUMERIC NOT NULL,
    insurance_volume NUMERIC NOT NULL,
    credit_previously NUMERIC NOT NULL
);

CREATE TABLE IF NOT EXISTS efficiency(
    id SERIAL PRIMARY KEY,
    productivity_level VARCHAR(64) NOT NULL,
    total_premium NUMERIC NOT NULL,
    premium_for_credits NUMERIC NOT NULL,
    fur_bonus NUMERIC NOT NULL,
    total_productivity NUMERIC NOT NULL,
    premium_insurance NUMERIC NOT NULL,
    premium_for_additional_products NUMERIC NOT NULL
);

CREATE TABLE IF NOT EXISTS criteria_bonus_for_fur(
    id SERIAL PRIMARY KEY,
    min_sum NUMERIC NOT NULL,
    min_sms NUMERIC NOT NULL,
    bonus NUMERIC NOT NULL
);

CREATE TABLE IF NOT EXISTS fur_result(
    id SERIAL PRIMARY KEY,
    bonus NUMERIC NOT NULL,
    count_credits_category_fur INTEGER NOT NULL,
    count_credits_category_fur_with_sms INTEGER NOT NULL,
    share_credits_category_fur_with_sms NUMERIC NOT NULL,
    sum_amount_credits_category_fur NUMERIC NOT NULL
);