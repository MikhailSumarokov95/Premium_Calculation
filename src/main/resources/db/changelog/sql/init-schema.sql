DROP TABLE IF EXISTS
insurance,
product_group,
credit,
preliminary_credit_result,
efficiency,
criteria_bonus_for_fur,
fur_result,
productivity_level,
productivity_result,
premium_limit,
insurance_result,
users
CASCADE;

CREATE TABLE IF NOT EXISTS users(
    id          BIGSERIAL PRIMARY KEY   NOT NULL,
    username    VARCHAR(64)             NOT NULL,
    password    VARCHAR(255)            NOT NULL,
    email       VARCHAR(255)            NOT NULL,
    role        VARCHAR(64)             NOT NULL,
    CONSTRAINT username_unique UNIQUE (username)
);

CREATE TABLE IF NOT EXISTS insurance(
    id                         BIGSERIAL PRIMARY KEY    NOT NULL,
    name                       VARCHAR(64)              NOT NULL,
    factor_insurance_volume    NUMERIC                  NOT NULL,
    factor_insurance_bonus     NUMERIC                  NOT NULL
);

CREATE TABLE IF NOT EXISTS product_group(
    id                                           BIGSERIAL PRIMARY KEY    NOT NULL,
    name                                         VARCHAR(64)              NOT NULL,
    factor_premium                               NUMERIC                  NOT NULL,
    min_premium                                  NUMERIC                  NOT NULL,
    max_premium                                  NUMERIC                  NOT NULL,
    type_credit                                  VARCHAR(64)              NOT NULL,
    min_amount_for_calculating_credit_premium    NUMERIC                  NOT NULL
);

CREATE TABLE IF NOT EXISTS credit(
    id                            BIGSERIAL PRIMARY KEY    NOT NULL,
    product_group_id              INTEGER                  NOT NULL REFERENCES product_group(id)
                                                           ON DELETE CASCADE,
    amount                        NUMERIC                  NOT NULL,
    term                          INTEGER                  NOT NULL,
    rate                          NUMERIC                  NOT NULL,
    insurance_id                  INTEGER                  NOT NULL REFERENCES insurance(id)
                                                           ON DELETE CASCADE,
    is_connected_sms              BOOLEAN                  NOT NULL DEFAULT FALSE,
    is_fur                        BOOLEAN                  NOT NULL DEFAULT FALSE,
    is_consultant_availability    BOOLEAN                  NOT NULL DEFAULT FALSE,
    is_used_self_reject           BOOLEAN                  NOT NULL DEFAULT FALSE,
    users_id                      INTEGER                  NOT NULL REFERENCES users(id)
                                                           ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS preliminary_credit_result(
    id                   BIGSERIAL PRIMARY KEY    NOT NULL,
    premium              NUMERIC                  NOT NULL,
    credit_total         NUMERIC                  NOT NULL,
    insurance_bonus      NUMERIC                  NOT NULL,
    insurance_volume     NUMERIC                  NOT NULL,
    credit_previously    NUMERIC                  NOT NULL,
    credit_id            BIGINT                   NOT NULL REFERENCES credit(id)
                                                  ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS efficiency(
    id                     BIGSERIAL PRIMARY KEY    NOT NULL,
    total_premium          NUMERIC                  NOT NULL,
    premium_for_credits    NUMERIC                  NOT NULL,
    fur_bonus              NUMERIC                  NOT NULL,
    total_productivity     NUMERIC                  NOT NULL,
    premium_insurance      NUMERIC                  NOT NULL,
    users_id               BIGINT                   NOT NULL REFERENCES users(id)
                                                    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS criteria_bonus_for_fur(
    id BIGSERIAL    PRIMARY KEY    NOT NULL,
    min_sum         NUMERIC        NOT NULL,
    min_sms         NUMERIC        NOT NULL,
    bonus           NUMERIC        NOT NULL
);

CREATE TABLE IF NOT EXISTS fur_result(
    id                                      BIGSERIAL PRIMARY KEY    NOT NULL,
    bonus                                   NUMERIC                  NOT NULL,
    count_credits_category_fur              BIGINT                   NOT NULL,
    count_credits_category_fur_with_sms     BIGINT                   NOT NULL,
    penetration_sms_credits_category_fur    NUMERIC                  NOT NULL,
    sum_amount_credits_category_fur         NUMERIC                  NOT NULL,
    users_id                                BIGINT                   NOT NULL REFERENCES users(id)
                                                                     ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS productivity_level(
    id                           BIGSERIAL PRIMARY KEY    NOT NULL,
    name                         VARCHAR(64)              NOT NULL,
    premium                      NUMERIC                  NOT NULL,
    min_count_credits            INTEGER                  NOT NULL,
    min_sum_amount_credits       NUMERIC                  NOT NULL,
    min_penetration_sms          NUMERIC                  NOT NULL,
    min_penetration_insurance    NUMERIC                  NOT NULL,
    is_default                   BOOLEAN                  DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS productivity_result(
    id                             BIGSERIAL PRIMARY KEY    NOT NULL,
    general_level                  INTEGER                  NOT NULL REFERENCES productivity_level(id)
                                                            ON DELETE CASCADE,
    sum_amount_credits_level       INTEGER                  NOT NULL REFERENCES productivity_level(id)
                                                            ON DELETE CASCADE,
    count_credits_level            INTEGER                  NOT NULL REFERENCES productivity_level(id)
                                                            ON DELETE CASCADE,
    insurance_penetration_level    INTEGER                  NOT NULL REFERENCES productivity_level(id)
                                                            ON DELETE CASCADE,
    sms_penetration_level          INTEGER                  NOT NULL REFERENCES productivity_level(id)
                                                            ON DELETE CASCADE,
    users_id                       BIGINT                   NOT NULL REFERENCES users(id)
                                                            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS premium_limit(
    id                   BIGSERIAL PRIMARY KEY    NOT NULL,
    max_total_premium    NUMERIC                  NOT NULL,
    is_actual            BOOLEAN                  DEFAULT false
);

CREATE UNIQUE INDEX ON premium_limit (is_actual)
WHERE is_actual = true;

CREATE TABLE IF NOT EXISTS insurance_result(
    id                      BIGSERIAL PRIMARY KEY    NOT NULL,
    total_bonus             NUMERIC                  NOT NULL,
    penetration             NUMERIC                  NOT NULL,
    sum_insurance_volume    NUMERIC                  NOT NULL,
    users_id                BIGINT                   NOT NULL REFERENCES users(id)
                                                     ON DELETE CASCADE
);