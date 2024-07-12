--shop users
INSERT INTO account (email,first_name,last_name) VALUES ('user@usa.net','John','Doe');
--departments and products
INSERT INTO department (name) VALUES ('Diary department'), ('Tea and Coffee');
INSERT INTO product_category (category_name,sale_limitation,storage_conditions) VALUES ('Dairy','None','Fridge'), ('Beverages','None','Dry');
INSERT INTO product (category_id,retail_price,description,name) VALUES (1,2.99,'a carton of fresh milk','Milk'), (2,5.99,'Roasted coffee beans','Coffee'), (2,3.49,'High-quality tea leaves','Tea');
INSERT INTO inventory (product_id,quantity,whole_sale_price,best_before,inventory_date) VALUES (1,100,2.5,'2024-07-18 17:39:40.112000','2024-07-11 17:39:40.112000'), (2,100,3.0,'2025-07-11 17:39:40.112000','2024-07-11 17:39:40.112000'), (3,50,5.5,'2025-01-09 16:39:40.112000','2024-07-11 17:39:40.112000');
INSERT INTO productallocation (department_id,product_id) VALUES (1,1), (2,2), (2,3);
--orders
INSERT INTO `order` (customer_id,total_amount,order_date,payment_method,status) VALUES (1,25.44,'2024-07-11 17:39:40.112000','Credit card','Completed');
INSERT INTO order_line (allocation_id,order_id,quantity) VALUES (1,1,1), (2,1,2), (3,1,3);
