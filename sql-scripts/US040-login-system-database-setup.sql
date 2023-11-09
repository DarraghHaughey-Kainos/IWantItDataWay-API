USE IWantItDataWay_StephenH;

CREATE TABLE `user` (
	username VARCHAR(64) NOT NULL,
	password VARCHAR(64) NOT NULL,
	PRIMARY KEY (username)
);

INSERT INTO `user`(username, password) VALUES
("hr","hr");