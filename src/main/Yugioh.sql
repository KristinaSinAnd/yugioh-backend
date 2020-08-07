-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema yugioh
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema yugioh
-- -----------------------------------------------------
CREATE DATABASE yugioh;
CREATE SCHEMA IF NOT EXISTS `yugioh` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `yugioh` ;

-- -----------------------------------------------------
-- Table `yugioh`.`articles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yugioh`.`articles` (
  `id_articles` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `booster_set` VARCHAR(30) NOT NULL,
  `card_name` VARCHAR(80) NOT NULL,
  `edition` VARCHAR(30) NOT NULL,
  `rarity` VARCHAR(30) NOT NULL,
  `card_type` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id_articles`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `yugioh`.`card_storage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yugioh`.`card_storage` (
  `id_card_storage` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `storage_name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id_card_storage`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `yugioh`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yugioh`.`roles` (
  `id_roles` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id_roles`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `yugioh`.`stock_items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yugioh`.`stock_items` (
  `id_stock_items` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `card_condition` VARCHAR(30) NOT NULL,
  `card_value` DECIMAL(10,0) NOT NULL,
  `card_value_when_sold` DECIMAL(10,0) NOT NULL,
  `in_shop` TINYINT(1) NOT NULL,
  `comments` VARCHAR(400) NOT NULL,
  `id_card_storage` INT(10) UNSIGNED NULL DEFAULT NULL,
  `id_articles` INT(10) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id_stock_items`),
  INDEX `id_card_storage` (`id_card_storage` ASC) VISIBLE,
  INDEX `id_articles` (`id_articles` ASC) VISIBLE,
  CONSTRAINT `stock_items_ibfk_1`
    FOREIGN KEY (`id_card_storage`)
    REFERENCES `yugioh`.`card_storage` (`id_card_storage`),
  CONSTRAINT `stock_items_ibfk_2`
    FOREIGN KEY (`id_articles`)
    REFERENCES `yugioh`.`articles` (`id_articles`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `yugioh`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `yugioh`.`users` (
  `id_users` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(30) NOT NULL,
  `user_surname` VARCHAR(30) NOT NULL,
  `user_email` VARCHAR(30) NOT NULL,
  `role_id` INT(10) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id_users`),
  INDEX `id_roles` (`id_roles` ASC) VISIBLE,
  CONSTRAINT `users_ibfk_1`
    FOREIGN KEY (`role_id`)
    REFERENCES `yugioh`.`roles` (`role_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
