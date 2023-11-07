USE IWantItDataWay_StephenH;

CREATE table hello_world (
	id smallint unsigned NOT NULL AUTO_INCREMENT,
    name varchar(70) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO hello_world (name) VALUES ("hello world");