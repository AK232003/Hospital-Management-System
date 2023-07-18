ALTER TABLE patient
    DROP FOREIGN KEY fk_medicine_id;

ALTER TABLE medicine
    DROP FOREIGN KEY fk_buyer;

DROP TABLE medicine;
DROP TABLE HospitalStaff;
DROP TABLE patient;
DROP TABLE super_admin;

DROP DATABASE Hospital;
