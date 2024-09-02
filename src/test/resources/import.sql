-- Adding new products
INSERT INTO product (category_id,retail_price,description,name) VALUES
                                                                    (1,25.00,'Premium cheese','Cheese'),
                                                                    (2,50.00,'Gourmet coffee beans','Gourmet Coffee');
-- Inserting new orders with high totals
INSERT INTO customer_order (customer_id,total_amount,order_date,payment_method,status) VALUES
                                                                                           (1,150.00,'2024-08-15 12:00:00.000000','Credit card','Completed'),
                                                                                           (2,120.00,'2024-08-20 16:00:00.000000','Cash','Completed');

-- Adding order lines to the new orders
INSERT INTO order_line (allocation_id,order_id,quantity) VALUES
                                                             -- For the $150 order
                                                             (1,5,3), -- 3 Cheeses at $25 each
                                                             (2,5,2), -- 2 Gourmet Coffees at $50 each
                                                             -- For the $120 order
                                                             (1,6,2), -- 2 Cheeses at $25 each
                                                             (2,6,1), -- 1 Gourmet Coffee at $50 each
                                                             (3,6,20); -- 20 Teas at $3.49 each
