USE IWantItDataWay_StephenH;

CREATE TABLE access_rights (
	access_right_id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	access_right VARCHAR(20) NOT NULL DEFAULT 'View'
);

INSERT INTO access_rights (access_right) VALUES
	('View'), ('Admin');

ALTER TABLE `user`
ADD access_right_id SMALLINT UNSIGNED;

ALTER TABLE `user`
ADD CONSTRAINT FK_AccessRight
FOREIGN KEY (access_right_id) REFERENCES access_rights(access_right_id);

UPDATE `user` SET access_right_id=1 WHERE access_right_id IS NULL;

-- alter table `user` drop constraint FK_PersonOrder;
-- ALTER TABLE `user`
-- DROP COLUMN accessRightId;