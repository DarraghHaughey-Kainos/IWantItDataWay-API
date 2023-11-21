USE IWantItDataWay_StephenH;

CREATE Table job_role (
job_role_id INT AUTO_INCREMENT PRIMARY KEY,
job_role_title VARCHAR(50) NOT NULL
);

INSERT INTO job_role (job_role_title)
VALUES
    ('Workday Test consultant'),
    ('Functional Consultant'),
    ('Senior Software Engineer'),
    ('Dynamics Solution Architect'),
    ('Senior Test Engineer'),
    ('Graduate Software Engineer'),
    ('Graduate Test Engineer'),
    ('Senior Test Manager'),
    ('Graduate Workday Consultant'),
    ('Workday AMS Integrations Consultant');