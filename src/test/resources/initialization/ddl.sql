-- Create table for product categories
CREATE TABLE product_category
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    category_name      VARCHAR(255) NOT NULL,
    sale_limitation    VARCHAR(255),
    storage_conditions VARCHAR(255)
);

-- Create table for departments
CREATE TABLE department
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    image_path VARCHAR(255) NOT NULL

);

-- Create table for products
CREATE TABLE product
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    category_id  INT,
    retail_price DECIMAL(10, 2),
    description  TEXT,
    name         VARCHAR(255) NOT NULL,
    image_url    VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES product_category (id)
);

-- Create table for inventory
CREATE TABLE inventory
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    product_id       INT,
    quantity         INT,
    whole_sale_price DECIMAL(10, 2),
    best_before      DATETIME,
    inventory_date   DATETIME,
    FOREIGN KEY (product_id) REFERENCES product (id)
);

-- Create table for accounts
CREATE TABLE account
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    email      VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255)        NOT NULL,
    last_name  VARCHAR(255)        NOT NULL
);

-- Create table for customer orders
CREATE TABLE customer_order
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    customer_id    INT,
    total_amount   DECIMAL(10, 2),
    order_date     DATETIME,
    payment_method ENUM ('CASH', 'CREDIT_CARD'),
    status         VARCHAR(255),
    FOREIGN KEY (customer_id) REFERENCES account (id)
);

-- Create table for order lines
CREATE TABLE order_line
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    allocation_id INT,
    order_id      INT,
    quantity      INT,
    FOREIGN KEY (order_id) REFERENCES customer_order (id)
);

-- Create table for product allocations
CREATE TABLE productallocation
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    department_id INT,
    product_id    INT,
    FOREIGN KEY (department_id) REFERENCES department (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);

-- Create table for product images
CREATE TABLE product_images
(
    product_name VARCHAR(255) PRIMARY KEY,
    image_url    VARCHAR(255)
);

CREATE TABLE USER_ROLES
(
    user_id INT          NOT NULL,
    role    VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USER (id)
);
