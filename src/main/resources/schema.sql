
CREATE TABLE IF NOT EXISTS `customers` (
    `customer_id` INT PRIMARY KEY AUTO_INCREMENT,
    `first_name` VARCHAR(50),
    `last_name` VARCHAR(50),
    `email` VARCHAR(100) UNIQUE,
    `phone_number` VARCHAR(20)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `address` (
    `address_id` INT PRIMARY KEY AUTO_INCREMENT,
    `street` VARCHAR(255),
    `city` VARCHAR(100),
    `state` VARCHAR(100),
    `zip_code` VARCHAR(20),
    `country` VARCHAR(100)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `orders` (
    `order_id` INT PRIMARY KEY AUTO_INCREMENT,
    `order_tracking_number` VARCHAR(255),
    `customer_id` INT,
    `order_address` INT,
    `total_price` DECIMAL(19,2),
    `total_quantity` INT,
    `date_create` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `date_update` timestamp default current_timestamp,
    `status` varchar(255),
    FOREIGN KEY (`order_address`) REFERENCES `address`(`address_id`),
    FOREIGN KEY (`customer_id`) REFERENCES `customers`(`customer_id`)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `product_category` (
    `category_id` INT PRIMARY KEY AUTO_INCREMENT,
    `category_name` VARCHAR(50) UNIQUE
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `product` (
    `product_id` INT PRIMARY KEY AUTO_INCREMENT,
    `category_id` INT,
    `name` VARCHAR(100),
    `description` TEXT,
    `price` DECIMAL(10, 2),
    `stock_quantity` INT,
    `product_img` varchar(255),
    `version` INT default 0,
    FOREIGN KEY (`category_id`) REFERENCES `product_category`(`category_id`)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `order_item` (
    `order_item_id` INT PRIMARY KEY AUTO_INCREMENT,
    `order_id` INT,
    `product_id` INT,
    `quantity` INT,
    `price` DECIMAL(10, 2),
    FOREIGN KEY (`order_id`) REFERENCES `orders`(`order_id`),
    FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `image_table` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255),
    `type` VARCHAR(255),
    `pic_byte` LONGBLOB,
    `product_id` INT,
    FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `_user` (
`id` INT AUTO_INCREMENT PRIMARY KEY,
`first_name` VARCHAR(255),
`last_name` VARCHAR(255),
`email` VARCHAR(255),
`password` VARCHAR(255),
`role` ENUM('USER', 'ADMIN')
)ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS `conversations` (
    `conversation_id` INT PRIMARY KEY AUTO_INCREMENT,
    `user1_id` INT,
    `user2_id` INT,
    FOREIGN KEY (`user1_id`) REFERENCES `_user`(`id`),
    FOREIGN KEY (`user2_id`) REFERENCES `_user`(`id`)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `messages` (
    `message_id` INT PRIMARY KEY AUTO_INCREMENT,
    `conversation_id` INT,
    `user_id` INT,
    `user_sender_id` INT,
    `content` TEXT,
    `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`conversation_id`) REFERENCES `conversations`(`conversation_id`),
    FOREIGN KEY (`user_id`) REFERENCES `_user`(`id`)
    ) ENGINE=InnoDB;



