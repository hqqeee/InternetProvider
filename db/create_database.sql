/***********************************************************
* Create the database named internet_provider and its tables
************************************************************/

DROP DATABASE IF EXISTS internet_provider;

CREATE DATABASE internet_provider;

USE internet_provider;

/***********************************************************/

CREATE TABLE `role` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(32) NOT NULL,
    `description` VARCHAR (255),
    PRIMARY KEY (`id`)
);


CREATE TABLE `user` (
    `id`          INT NOT NULL AUTO_INCREMENT,
    `password` CHAR(64) NOT NULL,
    `salt` CHAR(12) NOT NULL,
    `login` VARCHAR(32) NOT NULL UNIQUE,
    `role_id`     INT NOT NULL,
    `blocked`	BOOL NOT NULL DEFAULT FALSE,
    `email`      VARCHAR(32) UNIQUE,
    `first_name` VARCHAR(16),
    `last_name`  VARCHAR(16),
	`city`       VARCHAR(32),
	`address`	VARCHAR(32),
	`balance`   DECIMAL(8,2) NOT NULL DEFAULT 0,
    PRIMARY KEY(`id`),
    CONSTRAINT `fk_user_role`
        FOREIGN KEY(`role_id`) REFERENCES `role`(`id`)
        /*ON DELETE CASCADE
        ON UPDATE RESTRICT*/
);

/***********************************************************/

CREATE TABLE `service` (
    `id`        INT NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(32) NOT NULL,
    `description`VARCHAR(255),
    PRIMARY KEY (`id`)
);

CREATE TABLE `tariff` (
    `id`        INT NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(32) NOT NULL,
    `description`VARCHAR(255),
    `payment_period_days` INT NOT NULL,
    `rate`     DECIMAL(8,2) NOT NULL DEFAULT 0,
    `service_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_service_id`
        FOREIGN KEY (`service_id`) REFERENCES `service`(`id`)
        ON DELETE CASCADE
        ON UPDATE RESTRICT
);

CREATE TABLE `user_has_tariff` (
    `user_id`   INT NOT NULL,
    `tariff_id` INT NOT NULL,
    `days_until_next_payment` INT NOT NULL,
    PRIMARY KEY (`user_id`,`tariff_id`),
    CONSTRAINT `fk_uht_user`
        FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
        ON DELETE CASCADE
        ON UPDATE RESTRICT,
    CONSTRAINT `fk_uht_tariff`
        FOREIGN KEY (`tariff_id`) REFERENCES `tariff`(`id`)
        ON DELETE CASCADE
        ON UPDATE RESTRICT
);

/***********************************************************/

CREATE TABLE `transaction` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`user_id` INT NOT NULL,
	`timestamp` TIMESTAMP,
	`amount` DECIMAL(8,2) NOT NULL,
	`description` VARCHAR (128),
	PRIMARY KEY(`id`),
	CONSTRAINT `fk_user_id`
		FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
		ON DELETE CASCADE
		ON UPDATE RESTRICT
);