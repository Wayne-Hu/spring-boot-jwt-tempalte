CREATE DATABASE IF NOT EXISTS `JWT_TEMPLATE`
  DEFAULT CHARSET 'UTF8';

CREATE USER 'JwtTemplate'@'localhost'
  IDENTIFIED BY 'JwtTemplate';

GRANT ALL ON JWT_TEMPLATE.* TO 'JwtTemplate'@'localhost';

CREATE TABLE IF NOT EXISTS `USER_ROLE` (
  `id`   CHAR(32) PRIMARY KEY,
  `role` CHAR(16) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = 'UTF8';

CREATE TABLE IF NOT EXISTS `USER` (
  `id`           CHAR(32) PRIMARY KEY,
  `username`     CHAR(32)  NOT NULL,
  `password`     CHAR(64)  NOT NULL,
  `email`        CHAR(128) NOT NULL,
  `user_role_id` CHAR(32)  NOT NULL,
  KEY (`username`),
  KEY (`email`),
  CONSTRAINT `fk_user_user_role_id` FOREIGN KEY (`user_role_id`) REFERENCES `USER_ROLE` (`id`)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = 'UTF8';
