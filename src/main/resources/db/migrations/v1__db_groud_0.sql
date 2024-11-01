CREATE TABLE tb_vehicle(
    id BIGSERIAL PRIMARY KEY,
    manufacture_year VARCHAR(255),
    model VARCHAR(255),
    plate_number VARCHAR(10) NOT NULL UNIQUE,
    is_available BOOLEAN NOT NULL DEFAULT TRUE,
    vehicle_type VARCHAR(255)
);

CREATE TABLE tb_renter(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    birth_date DATE NOT NULL,
    cnh_number VARCHAR(9) UNIQUE,
    cnh_type VARCHAR(2),
    cnh_image VARCHAR(255)
);

CREATE TABLE tb_vehicle_rental (
    id BIGSERIAL PRIMARY KEY,
    vehicle_id BIGINT,
    renter_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    start_date DATE NOT NULL,
    due_date DATE NOT NULL,
    rental_vehicle_type VARCHAR(255) NOT NULL,
    FOREIGN KEY (vehicle_id) REFERENCES tb_vehicle(id),
    FOREIGN KEY (renter_id) REFERENCES tb_renter(id)
);



