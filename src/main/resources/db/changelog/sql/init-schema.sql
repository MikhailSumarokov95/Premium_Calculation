DROP TABLE IF EXISTS insurance, product_group, credit, preliminary_credit_result,
efficiency, criteria_bonus_for_fur, fur_result, productivity_level, productivity_result,
premium_limit, insurance_result, users CASCADE;

CREATE TABLE IF NOT EXISTS users(
    id SERIAL PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    role VARCHAR(64) NOT NULL,
    CONSTRAINT username_unique UNIQUE (username)
);

CREATE TABLE IF NOT EXISTS insurance(
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    factor_insurance_volume NUMERIC NOT NULL,
    factor_insurance_bonus NUMERIC NOT NULL
);

CREATE TABLE IF NOT EXISTS product_group(
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    factor_premium NUMERIC NOT NULL,
    min_premium NUMERIC NOT NULL,
    max_premium NUMERIC NOT NULL,
    type_credit VARCHAR(64) NOT NULL,
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
    is_used_self_reject BOOLEAN NOT NULL DEFAULT FALSE,
    users_id INTEGER NOT NULL REFERENCES users(id)
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
    users_id SERIAL PRIMARY KEY REFERENCES users(id),
    total_premium NUMERIC NOT NULL,
    premium_for_credits NUMERIC NOT NULL,
    fur_bonus NUMERIC NOT NULL,
    total_productivity NUMERIC NOT NULL,
    premium_insurance NUMERIC NOT NULL
);

CREATE TABLE IF NOT EXISTS criteria_bonus_for_fur(
    id SERIAL PRIMARY KEY,
    min_sum NUMERIC NOT NULL,
    min_sms NUMERIC NOT NULL,
    bonus NUMERIC NOT NULL
);

CREATE TABLE IF NOT EXISTS fur_result(
    users_id SERIAL PRIMARY KEY REFERENCES users(id),
    bonus NUMERIC NOT NULL,
    count_credits_category_fur BIGINT NOT NULL,
    count_credits_category_fur_with_sms BIGINT NOT NULL,
    penetration_sms_credits_category_fur NUMERIC NOT NULL,
    sum_amount_credits_category_fur NUMERIC NOT NULL
);

CREATE TABLE IF NOT EXISTS productivity_level(
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    premium NUMERIC NOT NULL,
    min_count_credits INTEGER NOT NULL,
    min_sum_amount_credits NUMERIC NOT NULL,
    min_penetration_sms NUMERIC NOT NULL,
    min_penetration_insurance NUMERIC NOT NULL
);

CREATE TABLE IF NOT EXISTS productivity_result(
    users_id SERIAL PRIMARY KEY REFERENCES users(id),
    general_level INTEGER NOT NULL REFERENCES productivity_level(id),
    sum_amount_credits_level INTEGER NOT NULL REFERENCES productivity_level(id),
    count_credits_level INTEGER NOT NULL REFERENCES productivity_level(id),
    insurance_penetration_level INTEGER NOT NULL REFERENCES productivity_level(id),
    sms_penetration_level INTEGER NOT NULL REFERENCES productivity_level(id)
);

CREATE TABLE IF NOT EXISTS premium_limit(
    id SERIAL PRIMARY KEY,
    max_total_premium NUMERIC NOT NULL
);

CREATE TABLE IF NOT EXISTS insurance_result(
    users_id SERIAL PRIMARY KEY REFERENCES users(id),
    total_bonus NUMERIC NOT NULL,
    penetration NUMERIC NOT NULL,
    sum_insurance_volume NUMERIC NOT NULL
);