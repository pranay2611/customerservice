CREATE ROLE customer WITH LOGIN PASSWORD 'customer';
CREATE DATABASE customers OWNER customer;
GRANT ALL PRIVILEGES ON DATABASE customers TO customer;

\connect customers postgres

CREATE TABLE IF NOT EXISTS customers (
    customer_id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(20),
    kyc_status VARCHAR(50) CHECK (kyc_status IN ('PENDING', 'REJECTED', 'VERIFIED')),
    created_at TIMESTAMP NOT NULL
);

COPY customers(name, email, phone, kyc_status, created_at)
FROM '/docker-entrypoint-initdb.d/data/customers.csv'
WITH (FORMAT csv, HEADER true);

ALTER TABLE customers OWNER TO customer;
