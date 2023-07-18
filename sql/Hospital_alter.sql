ALTER TABLE patient
    ADD CONSTRAINT fk_medicine_id FOREIGN KEY (medicine_id) REFERENCES medicine(medicine_id);

ALTER TABLE medicine
    ADD CONSTRAINT fk_buyer FOREIGN KEY (buyer) REFERENCES patient(patient_id);
