-- This script creates tables in the database and/or makes the structural changes to some existing tables.
-- Author: Dominik Harmim <harmim6@gmail.com>


SET NAMES utf8;


CREATE TABLE `module` (
	`module_id` VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
	`module_name` VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
	`credits` INT(10) UNSIGNED NOT NULL,
	PRIMARY KEY (`module_id`)
)
	ENGINE=InnoDB
	DEFAULT CHARSET=utf8
	COLLATE=utf8_unicode_ci;


CREATE TABLE `staff` (
	`staff_id` VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
	`staff_name` VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
	`staff_grade` VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
	PRIMARY KEY (`staff_id`)
)
	ENGINE=InnoDB
	DEFAULT CHARSET=utf8
	COLLATE=utf8_unicode_ci;


CREATE TABLE `student` (
	`student_id` VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
	`student_name` VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
	`degree_scheme` VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
	PRIMARY KEY (`student_id`)
)
	ENGINE=InnoDB
	DEFAULT CHARSET=utf8
	COLLATE=utf8_unicode_ci;


CREATE TABLE `teaches` (
	`staff_id` VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
	`module_id` VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
	PRIMARY KEY (`staff_id`, `module_id`),
	KEY `module_id` (`module_id`),
	KEY `staff_id` (`staff_id`),
	CONSTRAINT `teaches_fk_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`)
		ON DELETE CASCADE,
	CONSTRAINT `teaches_fk_module` FOREIGN KEY (`module_id`) REFERENCES `module` (`module_id`)
		ON DELETE CASCADE
)
	ENGINE=InnoDB
	DEFAULT CHARSET=utf8
	COLLATE=utf8_unicode_ci;


CREATE TABLE `registered` (
	`student_id` VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
	`module_id` VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
	PRIMARY KEY (`student_id`, `module_id`),
	KEY `module_id` (`module_id`),
	KEY `student_id` (`student_id`),
	CONSTRAINT `registered_fk_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`)
		ON DELETE CASCADE,
	CONSTRAINT `registered_fk_module` FOREIGN KEY (`module_id`) REFERENCES `module` (`module_id`)
		ON DELETE CASCADE
)
	ENGINE=InnoDB
	DEFAULT CHARSET=utf8
	COLLATE=utf8_unicode_ci;
