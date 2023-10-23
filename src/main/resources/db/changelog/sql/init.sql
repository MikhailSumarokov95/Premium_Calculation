DROP TABLE IF EXISTS credit CASCADE;

CREATE TABLE IF NOT EXISTS credit(
    id SERIAL PRIMARY KEY,
    category VARCHAR(64) NOT NULL,
    total DOUBLE PRECISION NOT NULL,
    term INTEGER NOT NULL,
    rate REAL NOT NULL,
    insurance VARCHAR(64) NOT NULL,
    is_connected_Sms BOOLEAN NOT NULL,
    is_consultant_availability BOOLEAN NOT NULL,
    is_used_self_reject BOOLEAN NOT NULL
);