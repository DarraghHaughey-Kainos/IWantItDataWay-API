USE IWantItDataWay_StephenH;

CREATE TABLE `band` (
	band_id tinyint unsigned AUTO_INCREMENT,
	band_name VARCHAR(50) NOT NULL,
	PRIMARY KEY (band_id)
);

INSERT INTO band (band_name) VALUES ("Apprentice"), ("Trainee"), ("Associate"), ("Senior Associate"), ("Consultant"), ("Manager"), ("Principal"), ("Leadership Community");

ALTER TABLE job_role
ADD band_id tinyint unsigned;

ALTER TABLE job_role
ADD FOREIGN KEY (band_id) REFERENCES band(band_id);

UPDATE job_role SET band_id = 3 WHERE job_role_id = 1;
UPDATE job_role SET band_id = 5 WHERE job_role_id = 2;
UPDATE job_role SET band_id = 4 WHERE job_role_id = 3;
UPDATE job_role SET band_id = 6 WHERE job_role_id = 4;
UPDATE job_role SET band_id = 4 WHERE job_role_id = 5;
UPDATE job_role SET band_id = 2 WHERE job_role_id = 6;
UPDATE job_role SET band_id = 2 WHERE job_role_id = 7;
UPDATE job_role SET band_id = 4 WHERE job_role_id = 8;
UPDATE job_role SET band_id = 2 WHERE job_role_id = 9;
UPDATE job_role SET band_id = 5 WHERE job_role_id = 10;

ALTER TABLE job_role MODIFY COLUMN band_id tinyint unsigned NOT NULL;

SELECT job_role_id, job_role_title, band_name 
FROM job_role
LEFT JOIN band USING(band_id);