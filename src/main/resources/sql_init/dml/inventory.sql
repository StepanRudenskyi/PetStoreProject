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