-- Based in http://stackoverflow.com/questions/10134044/mysql-stored-procedure-executing-show-create-table
delimiter '//'

CREATE PROCEDURE addcol() BEGIN
SET @dbName = (SELECT DATABASE());
IF NOT EXISTS(
	SELECT * FROM information_schema.COLUMNS
	WHERE COLUMN_NAME='logoLastModifiedTime' AND TABLE_NAME='groups' AND TABLE_SCHEMA=@dbName
	)
	THEN
		ALTER TABLE `groups`
		ADD COLUMN `logoLastModifiedTime` BIGINT NOT NULL DEFAULT 1347400051999;

END IF;
END;
//

delimiter ';'

CALL addcol();

DROP PROCEDURE addcol;
