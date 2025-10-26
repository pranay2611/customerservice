CREATE TABLE IF NOT EXISTS customers (
    customer_id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(20),
    kyc_status VARCHAR(50) CHECK (kyc_status IN ('PENDING', 'REJECTED', 'VERIFIED')),
    created_at TIMESTAMP NOT NULL
);
