-- Create Roles table (only if not exists)
CREATE TABLE IF NOT EXISTS roles (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Create Users table (only if not exists)
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL,
    role_id UUID NOT NULL,
    CONSTRAINT fk_role FOREIGN KEY(role_id) REFERENCES roles(id)
);

-- Create Resources table (only if not exists)
CREATE TABLE IF NOT EXISTS resources (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    resource_type VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Insert Roles only if not exists
INSERT INTO roles (id, name)
SELECT '1e9a1a20-0000-0000-0000-000000000001', 'ROLE_ADMIN'
WHERE NOT EXISTS (
    SELECT 1 FROM roles WHERE id = '1e9a1a20-0000-0000-0000-000000000001'
);

INSERT INTO roles (id, name)
SELECT '1e9a1a20-0000-0000-0000-000000000002', 'ROLE_USER'
WHERE NOT EXISTS (
    SELECT 1 FROM roles WHERE id = '1e9a1a20-0000-0000-0000-000000000002'
);

-- Insert Resources only if not exists
INSERT INTO resources (id, name, resource_type, capacity, active, created_at, updated_at)
SELECT '3b2c1d40-0000-0000-0000-000000000001', 'Conference Room A', 'HOTEL', 20, true, NOW(), NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM resources WHERE id = '3b2c1d40-0000-0000-0000-000000000001'
);

INSERT INTO resources (id, name, resource_type, capacity, active, created_at, updated_at)
SELECT '3b2c1d40-0000-0000-0000-000000000002', 'Projector X100', 'EQUIPMENT', 1, true, NOW(), NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM resources WHERE id = '3b2c1d40-0000-0000-0000-000000000002'
);

INSERT INTO resources (id, name, resource_type, capacity, active, created_at, updated_at)
SELECT '3b2c1d40-0000-0000-0000-000000000003', 'Van 15-seater', 'VEHICLE', 15, true, NOW(), NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM resources WHERE id = '3b2c1d40-0000-0000-0000-000000000003'
);

INSERT INTO resources (id, name, resource_type, capacity, active, created_at, updated_at)
SELECT '3b2c1d40-0000-0000-0000-000000000004', 'Laptop Dell 2025', 'EQUIPMENT', 1, true, NOW(), NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM resources WHERE id = '3b2c1d40-0000-0000-0000-000000000004'
);

INSERT INTO resources (id, name, resource_type, capacity, active, created_at, updated_at)
SELECT '3b2c1d40-0000-0000-0000-000000000005', 'Conference Room B', 'HOTEL', 30, true, NOW(), NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM resources WHERE id = '3b2c1d40-0000-0000-0000-000000000005'
);
