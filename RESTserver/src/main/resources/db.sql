-- MySQL Script generated by MySQL Workbench
-- 01/18/17 11:38:38
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema if072java
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema if072java
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `if072java` DEFAULT CHARACTER SET utf8 ;
USE `if072java` ;

-- -----------------------------------------------------
-- Table `if072java`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `if072java`.`user` ;

CREATE TABLE IF NOT EXISTS `if072java`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `email` VARCHAR(64) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `token` VARCHAR(64) NOT NULL,
  `is_enabled` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`email` ASC),
  UNIQUE INDEX `token_UNIQUE` (`token` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `if072java`.`unit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `if072java`.`unit` ;

CREATE TABLE IF NOT EXISTS `if072java`.`unit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `if072java`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `if072java`.`category` ;

CREATE TABLE IF NOT EXISTS `if072java`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256) NOT NULL,
  `user_id` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `userID_fk_idx` (`user_id` ASC),
  CONSTRAINT `category_userID_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `if072java`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `if072java`.`image`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `if072java`.`image` ;

CREATE TABLE IF NOT EXISTS `if072java`.`image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `image` LONGBLOB NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `if072java`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `if072java`.`product` ;

CREATE TABLE IF NOT EXISTS `if072java`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NOT NULL,
  `description` VARCHAR(256) NULL,
  `image_id` INT NULL,
  `user_id` INT NOT NULL DEFAULT 0,
  `category_id` INT NOT NULL DEFAULT 0,
  `unit_id` INT NOT NULL,
  `is_active` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  INDEX `userID_fk_idx` (`user_id` ASC),
  INDEX `categoryID_fk_idx` (`category_id` ASC),
  INDEX `unitID_fk_idx` (`unit_id` ASC),
  INDEX `product_image_id_idx` (`image_id` ASC),
  CONSTRAINT `product_userID_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `if072java`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `product_categoryID_fk`
    FOREIGN KEY (`category_id`)
    REFERENCES `if072java`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `product_unitID_fk`
    FOREIGN KEY (`unit_id`)
    REFERENCES `if072java`.`unit` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `product_image_id`
    FOREIGN KEY (`image_id`)
    REFERENCES `if072java`.`image` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `if072java`.`store`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `if072java`.`store` ;

CREATE TABLE IF NOT EXISTS `if072java`.`store` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256) NOT NULL,
  `address` VARCHAR(256) NULL,
  `user_id` INT NOT NULL DEFAULT 0,
  `is_active` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  INDEX `userID_fk_idx` (`user_id` ASC),
  CONSTRAINT `store_userID_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `if072java`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `if072java`.`stores_products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `if072java`.`stores_products` ;

CREATE TABLE IF NOT EXISTS `if072java`.`stores_products` (
  `product_id` INT NOT NULL,
  `store_id` INT NOT NULL,
  INDEX `productID_fk_idx` (`product_id` ASC),
  INDEX `storeID_fk_idx` (`store_id` ASC),
  PRIMARY KEY (`product_id`, `store_id`),
  CONSTRAINT `stores_products_productID_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `if072java`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `stores_products_storeID_fk`
    FOREIGN KEY (`store_id`)
    REFERENCES `if072java`.`store` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `if072java`.`storage`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `if072java`.`storage` ;

CREATE TABLE IF NOT EXISTS `if072java`.`storage` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `amount` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `userID_fk_idx` (`user_id` ASC),
  INDEX `productID_fk_idx` (`product_id` ASC),
  CONSTRAINT `storage_userID_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `if072java`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `storage_productID_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `if072java`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `if072java`.`analytics`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `if072java`.`analytics` ;

CREATE TABLE IF NOT EXISTS `if072java`.`analytics` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `amount` INT NOT NULL,
  `used_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `userID_idx` (`user_id` ASC),
  INDEX `productID_idx` (`product_id` ASC),
  CONSTRAINT `analytics_userID_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `if072java`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `analytics_productID_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `if072java`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `if072java`.`shopping_list`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `if072java`.`shopping_list` ;

CREATE TABLE IF NOT EXISTS `if072java`.`shopping_list` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `amount` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `userid_fk_idx` (`user_id` ASC),
  INDEX `productid_fk_idx` (`product_id` ASC),
  CONSTRAINT `shopping_list_userid_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `if072java`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `shopping_list_productid_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `if072java`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `if072java`.`cart`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `if072java`.`cart` ;

CREATE TABLE IF NOT EXISTS `if072java`.`cart` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `amount` INT NOT NULL,
  `store_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id_fk_idx` (`user_id` ASC),
  INDEX `cart_product_id_idx` (`product_id` ASC),
  INDEX `cart_store_id_idx` (`store_id` ASC),
  CONSTRAINT `cart_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `if072java`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `cart_product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `if072java`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `cart_store_id`
    FOREIGN KEY (`store_id`)
    REFERENCES `if072java`.`store` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `if072java`.`forecast`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `if072java`.`forecast` ;

CREATE TABLE IF NOT EXISTS `if072java`.`forecast` (
  `id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `end_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `forecast_user_id_idx` (`user_id` ASC),
  INDEX `forecast_product_id_idx` (`product_id` ASC),
  CONSTRAINT `forecast_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `if072java`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `forecast_product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `if072java`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


INSERT INTO user(name, email, password, token, is_enabled) VALUES('Vasia Pupkin', 'vasiapupkin@gmail.com', '1111', 'qljbelkjbi2pughiu2h23ui2oi3', 1);
INSERT INTO user(name, email, password, token, is_enabled) VALUES('Roman Dyndyn', 'romandyndyn@gmail.com', '2222', 'qasdflnsdafsfhiu2hfd3ui2oi3', 1);
INSERT INTO user(name, email, password, token, is_enabled) VALUES('Pavlo Bendus', 'pavlobendus@gmail.com', '3333', 'bgfbfgbfgbgpughiu2h23ui2oi3', 1);

INSERT INTO unit(name) VALUES('л');
INSERT INTO unit(name) VALUES('кг');
INSERT INTO unit(name) VALUES('шт');

INSERT INTO category(name, user_id) VALUES('Продовольчі товари', 0);
INSERT INTO category(name, user_id) VALUES('Техніка', 1);
INSERT INTO category(name, user_id) VALUES('Електроніка', 2);
INSERT INTO category(name, user_id) VALUES('Hi-Tech', 3);

INSERT INTO product(name, description, user_id, category_id, unit_id) VALUES('Олія', 'соняшникова, рафінована', 0, 1, 1);
INSERT INTO product(name, description, user_id, category_id, unit_id) VALUES('Apple MacBook Pro 16', 'остання модель', 1, 2, 3);
INSERT INTO product(name, description, user_id, category_id, unit_id) VALUES('Sony LCD', 'телевізор з FULL-HD розширенням', 2, 3, 3);
INSERT INTO product(name, description, user_id, category_id, unit_id) VALUES('Google Glasses', 'розумні окуляри', 3, 4, 3);

INSERT INTO store(name, address, user_id) VALUES('Вопак', 'Галицька, 20', 0);
INSERT INTO store(name, address, user_id) VALUES('Ельдорадо', 'Вовчинецька, 40', 1);
INSERT INTO store(name, address, user_id) VALUES('MOYO', 'Вовчинецька, 40', 2);
INSERT INTO store(name, address, user_id) VALUES('Rozetka', 'Інтернет', 3);

INSERT INTO stores_products(product_id, store_id) VALUES(1, 1);
INSERT INTO stores_products(product_id, store_id) VALUES(2, 2);
INSERT INTO stores_products(product_id, store_id) VALUES(3, 3);
INSERT INTO stores_products(product_id, store_id) VALUES(4, 4);

INSERT INTO storage(user_id, product_id, amount) VALUES(1, 1, 5);
INSERT INTO storage(user_id, product_id, amount) VALUES(2, 1, 3);
INSERT INTO storage(user_id, product_id, amount) VALUES(3, 4, 1);
INSERT INTO storage(user_id, product_id, amount) VALUES(2, 3, 1);
INSERT INTO storage(user_id, product_id, amount) VALUES(3, 2, 2);
INSERT INTO storage(user_id, product_id, amount) VALUES(1, 2, 1);

INSERT INTO shopping_list(user_id, product_id, amount) VALUES(1, 3, 0);
INSERT INTO shopping_list(user_id, product_id, amount) VALUES(2, 2, 0);
INSERT INTO shopping_list(user_id, product_id, amount) VALUES(2, 4, 0);
INSERT INTO shopping_list(user_id, product_id, amount) VALUES(3, 1, 1);

INSERT INTO cart(user_id, product_id, amount, store_id) VALUES(1, 4, 0, 4);
INSERT INTO cart(user_id, product_id, amount, store_id) VALUES(3, 3, 0, 3);

INSERT INTO analytics(user_id, product_id, amount, used_date) VALUES(1, 1, 1, '2017-01-18');

INSERT INTO forecast(user_id, product_id, end_date) VALUES(1, 1, '2017-01-23');