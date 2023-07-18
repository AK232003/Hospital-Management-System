CREATE DATABASE Hospital;
USE Hospital;

GRANT ALL PRIVILEGES ON Hospital.* TO 'user10'@'localhost';

CREATE TABLE patient (
    patient_id VARCHAR(100) NOT NULL,
    patient_name VARCHAR(100) NOT NULL,
    medicine_id VARCHAR(100),
    CONSTRAINT pk_patient PRIMARY KEY (patient_id)
);

CREATE TABLE medicine (
    medicine_id VARCHAR(100) NOT NULL,
    medicine_name VARCHAR(100) NOT NULL,
    medicine_disease VARCHAR(100) NOT NULL,
    medicine_year INTEGER NOT NULL,
    buyer VARCHAR(100),
    CONSTRAINT pk_medicine PRIMARY KEY (medicine_id)
);

CREATE TABLE HospitalStaff (
    staff_id VARCHAR(100) NOT NULL,
    staff_name VARCHAR(100) NOT NULL,
    staff_password VARCHAR(100) NOT NULL,
    CONSTRAINT pk_staff PRIMARY KEY (staff_id)
);

CREATE TABLE super_admin (
    super_admin_id VARCHAR(100) NOT NULL,
    super_admin_name VARCHAR(100) NOT NULL,
    super_admin_password VARCHAR(100) NOT NULL,
    CONSTRAINT pk_super_admin PRIMARY KEY (super_admin_id)
);
