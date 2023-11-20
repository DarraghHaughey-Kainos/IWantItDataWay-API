USE IWantItDataWay_StephenH;

CREATE TABLE capability (
	capability_id TINYINT UNSIGNED NOT NULL auto_increment,
    capability_name VARCHAR(50) NOT NULL,
    PRIMARY KEY(capability_id)
);

INSERT INTO capability (capability_id, capability_name) VALUES (1,'Applied Innovation');
INSERT INTO capability (capability_id, capability_name) VALUES (2,'Business Development & Marketing');
INSERT INTO capability (capability_id, capability_name) VALUES (3,'Business Service Support');
INSERT INTO capability (capability_id, capability_name) VALUES (4, 'Data & AI');
INSERT INTO capability (capability_id, capability_name) VALUES (5, 'Delivery Management');
INSERT INTO capability (capability_id, capability_name) VALUES (6, 'Engineering');
INSERT INTO capability (capability_id, capability_name) VALUES (7, 'Experience Design');
INSERT INTO capability (capability_id, capability_name) VALUES (8, 'Platforms');
INSERT INTO capability (capability_id, capability_name) VALUES (9, 'Pre-Sales');
INSERT INTO capability (capability_id, capability_name) VALUES (10, 'Product & Digital Advisory');
INSERT INTO capability (capability_id, capability_name) VALUES (11, 'Quality Assurance');

ALTER TABLE job_role
ADD capability_id TINYINT UNSIGNED NOT NULL;

SELECT * FROM job_role;

UPDATE job_role SET capability_id = 6 WHERE job_role_id = 10;

ALTER TABLE job_role
ADD CONSTRAINT fk_job_role_capability_id
	FOREIGN KEY(capability_id)
    REFERENCES capability(capability_id);
    
SELECT job_role_id, job_role_title, capability_name FROM job_role
JOIN capability
USING(capability_id);