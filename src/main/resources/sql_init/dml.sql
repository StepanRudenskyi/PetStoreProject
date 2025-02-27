-- fill product category table with data
INSERT INTO product_category (category_name, sale_limitation, storage_conditions)
VALUES ('Fruits & Veggies', 'None', 'Store in a cool, dry place'),
       ('Meat & Fish', 'None', 'Keep refrigerated or frozen'),
       ('Dairy & Eggs', 'None', 'Keep in refrigerator'),
       ('Bakery', 'None', 'Store in a cool, dry place'),
       ('Pantry Staples', 'None', 'Store in a cool, dry place'),
       ('Sweets & Snacks', 'None', 'Store in a cool, dry place'),
       ('Beverages', 'None', 'Store in a cool, dry place'),
       ('Organic & Dietary Products', 'None', 'Store as per product label'),
       ('Health & Beauty', 'None', 'Store in a cool, dry place');

-- fill department table with data
INSERT INTO department (name, image_path)
VALUES ('Fruits & Veggies Department', '/images/categories/fruits-and-veggies.png'),
       ('Meat & Fish Department', '/images/categories/meat-fish.png'),
       ('Dairy & Eggs Department', '/images/categories/diary-eggs.png'),
       ('Bakery Department', '/images/categories/bakery.png'),
       ('Pantry Staples Department', '/images/categories/pantry-staples.png'),
       ('Sweets & Snacks Department', '/images/categories/sweets-snacks.png'),
       ('Beverages Department', '/images/categories/beverages.png'),
       ('Organic & Dietary Products Department', '/images/categories/organic-dietary.png'),
       ('Health & Beauty Department', '/images/categories/health-beauty.png');

-- fill product table with data
-- Fruits & Veggies
INSERT INTO product (category_id, retail_price, description, name)
VALUES (1, 1.99, 'Fresh apples', 'Apple'),
       (1, 2.49, 'Fresh bananas', 'Banana'),
       (1, 3.99, 'Organic carrots', 'Carrot'),
       (1, 4.99, 'Juicy oranges', 'Orange');

-- Meat & Fish
INSERT INTO product (category_id, retail_price, description, name)
VALUES (2, 9.99, 'Chicken breasts', 'Chicken Breast'),
       (2, 12.49, 'Ground beef', 'Ground Beef'),
       (2, 15.99, 'Salmon fillets', 'Salmon Fillet'),
       (2, 7.99, 'Pork chops', 'Pork Chop');

-- Dairy & Eggs
INSERT INTO product (category_id, retail_price, description, name)
VALUES (3, 2.49, 'Whole milk', 'Milk'),
       (3, 1.99, 'Free-range eggs', 'Eggs'),
       (3, 3.99, 'Butter', 'Butter'),
       (3, 2.99, 'Cheddar cheese', 'Cheese');

-- Bakery
INSERT INTO product (category_id, retail_price, description, name)
VALUES (4, 2.99, 'Whole grain bread', 'Bread'),
       (4, 3.49, 'Croissants', 'Croissant'),
       (4, 4.99, 'Bagels', 'Bagels'),
       (4, 5.49, 'Muffins', 'Muffins');

-- Pantry Staples
INSERT INTO product (category_id, retail_price, description, name)
VALUES (5, 1.49, 'White rice', 'White Rice'),
       (5, 2.99, 'Spaghetti pasta', 'Spaghetti'),
       (5, 1.99, 'Canned tomatoes', 'Canned Tomatoes'),
       (5, 3.49, 'Olive oil', 'Olive Oil');

-- Sweets & Snacks
INSERT INTO product (category_id, retail_price, description, name)
VALUES (6, 2.49, 'Chocolate bars', 'Chocolate Bar'),
       (6, 1.99, 'Potato chips', 'Potato Chips'),
       (6, 3.99, 'Gummy bears', 'Gummy Bears'),
       (6, 4.49, 'Cookies', 'Cookies');

-- Beverages
INSERT INTO product (category_id, retail_price, description, name)
VALUES (7, 1.29, 'Orange juice', 'Orange Juice'),
       (7, 2.49, 'Sparkling water', 'Sparkling Water'),
       (7, 3.99, 'Coffee beans', 'Coffee Beans'),
       (7, 1.99, 'Green tea', 'Green Tea');

-- Organic & Dietary Products
INSERT INTO product (category_id, retail_price, description, name)
VALUES (8, 4.99, 'Organic quinoa', 'Quinoa'),
       (8, 5.49, 'Chia seeds', 'Chia Seeds'),
       (8, 3.99, 'Almond milk', 'Almond Milk'),
       (8, 2.99, 'Gluten-free pasta', 'Gluten-Free Pasta');

-- Health & Beauty
INSERT INTO product (category_id, retail_price, description, name)
VALUES (9, 7.99, 'Shampoo', 'Shampoo'),
       (9, 5.49, 'Body lotion', 'Body Lotion'),
       (9, 2.99, 'Toothpaste', 'Toothpaste'),
       (9, 4.99, 'Face cream', 'Face Cream');


-- fill account table with data
INSERT INTO account (email, first_name, last_name)
VALUES ('example@example.com', 'Init User', 'Example'),
       ('john.doe@example.com', 'John', 'Doe'),
       ('jane.smith@example.com', 'Jane', 'Smith'),
       ('alice.johnson@example.com', 'Alice', 'Johnson'),
       ('bob.brown@example.com', 'Bob', 'Brown');

-- fill customer_order table with data
INSERT INTO customer_order (customer_id, total_amount, order_date, payment_method, status)
VALUES (1, 26.42, '2024-09-01 10:00:00', 'Credit Card', 'Completed'),
       (2, 42.90, '2024-09-02 11:30:00', 'PayPal', 'Pending'),
       (3, 46.91, '2024-09-03 14:15:00', 'Credit Card', 'Shipped'),
       (4, 58.92, '2024-09-04 09:45:00', 'Cash', 'Delivered');


-- fill inventory table with data
-- Fruits & Veggies
INSERT INTO inventory (product_id, quantity, whole_sale_price, best_before, inventory_date)
VALUES (1, 100, 1.20, '2024-12-01 00:00:00', '2024-09-01 00:00:00'), -- Apple
       (2, 120, 1.50, '2024-11-15 00:00:00', '2024-09-01 00:00:00'), -- Banana
       (3, 80, 2.50, '2024-11-30 00:00:00', '2024-09-01 00:00:00'),  -- Carrot
       (4, 90, 3.00, '2024-12-10 00:00:00', '2024-09-01 00:00:00');  -- Orange

-- Meat & Fish
INSERT INTO inventory (product_id, quantity, whole_sale_price, best_before, inventory_date)
VALUES (5, 50, 7.00, '2024-10-05 00:00:00', '2024-09-01 00:00:00'),  -- Chicken Breast
       (6, 40, 9.00, '2024-10-08 00:00:00', '2024-09-01 00:00:00'),  -- Ground Beef
       (7, 30, 12.00, '2024-09-30 00:00:00', '2024-09-01 00:00:00'), -- Salmon Fillet
       (8, 60, 5.50, '2024-10-15 00:00:00', '2024-09-01 00:00:00');  -- Pork Chop

-- Dairy & Eggs
INSERT INTO inventory (product_id, quantity, whole_sale_price, best_before, inventory_date)
VALUES (9, 150, 1.70, '2024-09-25 00:00:00', '2024-09-01 00:00:00'),  -- Milk
       (10, 200, 1.20, '2024-09-20 00:00:00', '2024-09-01 00:00:00'), -- Eggs
       (11, 100, 2.50, '2024-12-01 00:00:00', '2024-09-01 00:00:00'), -- Butter
       (12, 120, 2.00, '2024-12-15 00:00:00', '2024-09-01 00:00:00'); -- Cheese

-- Bakery
INSERT INTO inventory (product_id, quantity, whole_sale_price, best_before, inventory_date)
VALUES (13, 70, 2.00, '2024-09-20 00:00:00', '2024-09-01 00:00:00'), -- Bread
       (14, 50, 2.50, '2024-09-15 00:00:00', '2024-09-01 00:00:00'), -- Croissant
       (15, 60, 3.00, '2024-09-25 00:00:00', '2024-09-01 00:00:00'), -- Bagels
       (16, 40, 3.50, '2024-09-20 00:00:00', '2024-09-01 00:00:00'); -- Muffins

-- Pantry Staples
INSERT INTO inventory (product_id, quantity, whole_sale_price, best_before, inventory_date)
VALUES (17, 200, 1.00, '2025-01-01 00:00:00', '2024-09-01 00:00:00'), -- White Rice
       (18, 150, 2.00, '2025-01-01 00:00:00', '2024-09-01 00:00:00'), -- Spaghetti
       (19, 180, 1.20, '2025-01-01 00:00:00', '2024-09-01 00:00:00'), -- Canned Tomatoes
       (20, 100, 2.50, '2025-01-01 00:00:00', '2024-09-01 00:00:00'); -- Olive Oil

-- Sweets & Snacks
INSERT INTO inventory (product_id, quantity, whole_sale_price, best_before, inventory_date)
VALUES (21, 100, 1.80, '2025-02-01 00:00:00', '2024-09-01 00:00:00'), -- Chocolate Bar
       (22, 150, 1.30, '2024-12-01 00:00:00', '2024-09-01 00:00:00'), -- Potato Chips
       (23, 120, 2.50, '2025-02-01 00:00:00', '2024-09-01 00:00:00'), -- Gummy Bears
       (24, 90, 3.00, '2025-02-01 00:00:00', '2024-09-01 00:00:00');  -- Cookies

-- Beverages
INSERT INTO inventory (product_id, quantity, whole_sale_price, best_before, inventory_date)
VALUES (25, 150, 0.80, '2025-06-01 00:00:00', '2024-09-01 00:00:00'), -- Orange Juice
       (26, 120, 1.70, '2025-06-01 00:00:00', '2024-09-01 00:00:00'), -- Sparkling Water
       (27, 80, 2.80, '2025-06-01 00:00:00', '2024-09-01 00:00:00'),  -- Coffee Beans
       (28, 100, 1.20, '2025-06-01 00:00:00', '2024-09-01 00:00:00'); -- Green Tea

-- Organic & Dietary Products
INSERT INTO inventory (product_id, quantity, whole_sale_price, best_before, inventory_date)
VALUES (29, 70, 3.50, '2025-01-01 00:00:00', '2024-09-01 00:00:00'),  -- Quinoa
       (30, 60, 4.00, '2025-01-01 00:00:00', '2024-09-01 00:00:00'),  -- Chia Seeds
       (31, 100, 2.80, '2025-01-01 00:00:00', '2024-09-01 00:00:00'), -- Almond Milk
       (32, 120, 2.20, '2025-01-01 00:00:00', '2024-09-01 00:00:00'); -- Gluten-Free Pasta

-- Health & Beauty
INSERT INTO inventory (product_id, quantity, whole_sale_price, best_before, inventory_date)
VALUES (33, 40, 5.00, '2025-03-01 00:00:00', '2024-09-01 00:00:00'),  -- Shampoo
       (34, 50, 3.50, '2025-03-01 00:00:00', '2024-09-01 00:00:00'),  -- Body Lotion
       (35, 100, 1.70, '2025-03-01 00:00:00', '2024-09-01 00:00:00'), -- Toothpaste
       (36, 80, 3.00, '2025-03-01 00:00:00', '2024-09-01 00:00:00');  -- Face Cream


-- fill order_line table with data
INSERT INTO order_line (allocation_id, order_id, quantity)
VALUES (1, 1, 2),
       (5, 1, 1),
       (9, 1, 3),
       (21, 1, 2),
       -- Total: 26.42

       (2, 2, 4),
       (6, 2, 2),
       (10, 2, 1),
       (25, 2, 3),
       -- Total: 42.90

       (3, 3, 5),
       (7, 3, 1),
       (12, 3, 2),
       (29, 3, 1),
       -- Total: 46.91

       (6, 4, 2),
       (15, 4, 3),
       (30, 4, 2),
       (8, 4, 1);
       -- Total: 58.92

-- fill productallocation table with data
INSERT INTO productallocation (department_id, product_id)
VALUES (1, 1),  -- ID 1 (Apple) category Fruits & Veggies
       (1, 2),  -- ID 2 (Banana) category Fruits & Veggies
       (1, 3),  -- ID 3 (Carrot) category Fruits & Veggies
       (1, 4),  -- ID 4 (Orange) category Fruits & Veggies

       (2, 5),  -- ID 5 (Chicken Breast) category Meat & Fish
       (2, 6),  -- ID 6 (Ground Beef) category Meat & Fish
       (2, 7),  -- ID 7 (Salmon Fillet) category Meat & Fish
       (2, 8),  -- ID 8 (Pork Chop) category Meat & Fish

       (3, 9),  -- ID 9 (Milk) category Dairy & Eggs
       (3, 10), -- ID 10 (Eggs) category Dairy & Eggs
       (3, 11), -- ID 11 (Butter) category Dairy & Eggs
       (3, 12), -- ID 12 (Cheese) category Dairy & Eggs

       (4, 13), -- ID 13 (Bread) category Bakery
       (4, 14), -- ID 14 (Croissant) category Bakery
       (4, 15), -- ID 15 (Bagel) category Bakery
       (4, 16), -- ID 16 (Muffin) category Bakery

       (5, 17), -- ID 17 (White Rice) category Pantry Staples
       (5, 18), -- ID 18 (Spaghetti) category Pantry Staples
       (5, 19), -- ID 19 (Canned Tomatoes) category Pantry Staples
       (5, 20), -- ID 20 (Olive Oil) category Pantry Staples

       (6, 21), -- ID 21 (Chocolate Bar) category Sweets & Snacks
       (6, 22), -- ID 22 (Potato Chips) category Sweets & Snacks
       (6, 23), -- ID 23 (Gummy Bears) category Sweets & Snacks
       (6, 24), -- ID 24 (Cookies) category Sweets & Snacks

       (7, 25), -- ID 25 (Orange Juice) category Beverages
       (7, 26), -- ID 26 (Sparkling Water) category Beverages
       (7, 27), -- ID 27 (Coffee Beans) category Beverages
       (7, 28), -- ID 28 (Green Tea) category Beverages

       (8, 29), -- ID 29 (Quinoa) category Organic & Dietary Products
       (8, 30), -- ID 30 (Chia Seeds) category Organic & Dietary Products
       (8, 31), -- ID 31 (Almond Milk) category Organic & Dietary Products
       (8, 32), -- ID 32 (Gluten-Free Pasta) category Organic & Dietary Products

       (9, 33), -- ID 33 (Shampoo) category Health & Beauty
       (9, 34), -- ID 34 (Body Lotion) category Health & Beauty
       (9, 35), -- ID 35 (Toothpaste) category Health & Beauty
       (9, 36); -- ID 36 (Face Cream) category Health & Beauty


CREATE TABLE product_images
(
    product_name VARCHAR(255) PRIMARY KEY,
    image_url    VARCHAR(255)
);



INSERT INTO product_images (product_name, image_url)
VALUES ('Almond Milk', '/static/images/products/almondMilk.png'),
       ('Apple', '/static/images/products/apple.png'),
       ('Bagels', '/static/images/products/bagels.png'),
       ('Banana', '/static/images/products/banana.png'),
       ('Body Lotion', '/static/images/products/bodyLotion.png'),
       ('Bread', '/static/images/products/wholeGrainBread.png'),
       ('Butter', '/static/images/products/butter.png'),
       ('Canned Tomatoes', '/static/images/products/cannedTomatoes.png'),
       ('Carrot', '/static/images/products/carrot.png'),
       ('Cheese', '/static/images/products/cheese.png'),
       ('Chia Seeds', '/static/images/products/chiaSeeds.png'),
       ('Chicken Breast', '/static/images/products/chicken.png'),
       ('Chocolate Bar', '/static/images/products/chocolateBar.png'),
       ('Coffee Beans', '/static/images/products/coffeeBeans.png'),
       ('Cookies', '/static/images/products/cookies.png'),
       ('Croissant', '/static/images/products/croissant.png'),
       ('Eggs', '/static/images/products/eggs.png'),
       ('Face Cream', '/static/images/products/faceCream.png'),
       ('Gluten-Free Pasta', '/static/images/products/gluten-FreePasta.png'),
       ('Green Tea', '/static/images/products/greenTea.png'),
       ('Ground Beef', '/static/images/products/groundBeef.png'),
       ('Gummy Bears', '/static/images/products/gummyBears.png'),
       ('Milk', '/static/images/products/milk.png'),
       ('Muffins', '/static/images/products/muffins.png'),
       ('Olive Oil', '/static/images/products/oliveOil.png'),
       ('Orange', '/static/images/products/orange.png'),
       ('Orange Juice', '/static/images/products/orangeJuice.png'),
       ('Pork Chop', '/static/images/products/porkChop.png'),
       ('Potato Chips', '/static/images/products/potatoChips.png'),
       ('Quinoa', '/static/images/products/quinoa.png'),
       ('Salmon Fillet', '/static/images/products/salmonFillet.png'),
       ('Shampoo', '/static/images/products/shampoo.png'),
       ('Spaghetti', '/static/images/products/spaghetti.png'),
       ('Sparkling Water', '/static/images/products/sparklingWater.png'),
       ('Toothpaste', '/static/images/products/toothpaste.png'),
       ('White Rice', '/static/images/products/whiteRice.png');



ALTER TABLE product
    ADD COLUMN image_url VARCHAR(255);


UPDATE product p
    JOIN product_images pi
    ON p.name = pi.product_name
SET p.image_url = pi.image_url;


UPDATE product p
SET p.image_url = REPLACE(p.image_url, '/static/', '/');

DROP TABLE product_images;


INSERT INTO user (password, username) # add admin with password 1234
values ('$2a$10$M2yzYkziVXElZKNmlgkgAuCxA.FfZJn7wNvH7ZL7BlmTja0jRjVMW', 'admin');

INSERT INTO user_roles (user_id, role)
    value ('8', 'ADMIN');

UPDATE account a
SET a.user_id = 8
where a.id = 2;


# add image path to the product_category table

ALTER TABLE department
    ADD COLUMN image_path VARCHAR(255);
