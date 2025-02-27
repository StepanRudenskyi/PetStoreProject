CREATE DATABASE IF NOT EXISTS petstore;

USE petstore;

-- Create table for product categories
CREATE TABLE IF NOT EXISTS product_category
(
    category_id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_name      VARCHAR(255) NOT NULL,
    sale_limitation    VARCHAR(255),
    storage_conditions VARCHAR(255)
);


-- Create table for departments
CREATE TABLE IF NOT EXISTS department
(
    department_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    image_path    VARCHAR(255)
);

-- Create table for products
CREATE TABLE IF NOT EXISTS product
(
    product_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id  BIGINT,
    retail_price DECIMAL(38, 2) NOT NULL,
    description  VARCHAR(255),
    name         VARCHAR(255)   NOT NULL,
    image_url    VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES product_category (category_id) ON DELETE SET NULL
);

-- Create table for inventory
CREATE TABLE IF NOT EXISTS inventory
(
    inventory_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id       BIGINT      NOT NULL,
    quantity         INT         NOT NULL,
    whole_sale_price DECIMAL(38, 2),
    best_before      DATETIME(6),
    inventory_date   DATETIME(6) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE
);

-- Create table for accounts
CREATE TABLE IF NOT EXISTS account
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    user_id    BIGINT       NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
);
-- Create table for customer orders
CREATE TABLE IF NOT EXISTS customer_order
(
    order_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id    BIGINT         NOT NULL,
    total_amount   DECIMAL(38, 2) NOT NULL,
    order_date     DATETIME(6)    NOT NULL,
    payment_method VARCHAR(255),
    status         VARCHAR(255),
    FOREIGN KEY (customer_id) REFERENCES account (id) ON DELETE CASCADE
);

-- Create table for order lines
CREATE TABLE IF NOT EXISTS order_line
(
    order_line_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id      BIGINT NOT NULL,
    allocation_id BIGINT NOT NULL,
    quantity      INT    NOT NULL,
    FOREIGN KEY (order_id) REFERENCES customer_order (order_id) ON DELETE CASCADE,
    FOREIGN KEY (allocation_id) REFERENCES product (product_id) ON DELETE CASCADE
);

-- Create table for product allocations
CREATE TABLE IF NOT EXISTS product_allocation
(
    department_id BIGINT NOT NULL,
    product_id    BIGINT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE,
    FOREIGN KEY (department_id) REFERENCES department (department_id) ON DELETE CASCADE
);

-- Create table for users
CREATE TABLE IF NOT EXISTS user
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Create table for user roles
CREATE TABLE IF NOT EXISTS user_roles
(
    user_id BIGINT NOT NULL,
    role    VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
);

-- Create table for cart
CREATE TABLE IF NOT EXISTS cart
(
    cart_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT      NOT NULL,
    product_id    BIGINT      NOT NULL,
    quantity      INT         NOT NULL,
    addition_date DATETIME(6) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
);