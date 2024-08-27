-- shop users
INSERT INTO account (email,first_name,last_name) VALUES ('user@usa.net','John','Doe'), ('user2@gmail.com', 'Alex','Smith');
-- departments and products
INSERT INTO department (name) VALUES ('Diary department'), ('Tea and Coffee'), ('Water');
INSERT INTO product_category (category_name,sale_limitation,storage_conditions) VALUES
                                                                                    ('Dairy','None','Fridge'),
                                                                                    ('Beverages','None','Dry');
INSERT INTO product (category_id,retail_price,description,name) VALUES
                                                                    (1,2.99,'a carton of fresh milk','Milk'),
                                                                    (2,5.99,'Roasted coffee beans','Coffee'),
                                                                    (2,3.49,'High-quality tea leaves','Tea'),
                                                                    (2,0.99,'a plastic bottle of water','Water');
INSERT INTO inventory (product_id,quantity,whole_sale_price,best_before,inventory_date) VALUES
                                                                                            (1,100,2.5,'2024-07-18 17:39:40.112000','2024-07-11 17:39:40.112000'),
                                                                                            (2,100,3.0,'2025-07-11 17:39:40.112000','2024-07-11 17:39:40.112000'),
                                                                                            (3,50,5.5,'2025-01-09 16:39:40.112000','2024-07-11 17:39:40.112000'),
                                                                                            (4,500,0.79,'2026-01-01 16:39:40.112000','2024-07-11 17:39:40.112000');
INSERT INTO productallocation (department_id,product_id) VALUES (1,1), (2,2), (2,3), (3,4);
-- orders
INSERT INTO customer_order (customer_id,total_amount,order_date,payment_method,status) VALUES (1,25.44,'2024-07-11 17:39:40.112000','Credit card','Completed'),
                                                                                       (1,7.97,'2024-07-03 11:30:10.010000','Credit card','Completed'),
                                                                                       (1,20.41,'2024-07-14 09:18:32.107324','Cash','Completed'),
                                                                                        (2,9.99,'2024-07-15 19:18:32.107324','Credit Card','Completed');
INSERT INTO order_line (allocation_id,order_id,quantity) VALUES (1,1,1), (2,1,2), (3,1,3),
                                                                (4,2,2), (2,2,1),
                                                                (1,3,2), (2,3,1), (3,3,1), (4,3,5),
                                                                (4,4,10);