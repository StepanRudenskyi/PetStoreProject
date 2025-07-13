-- fill account table with data
INSERT INTO account (email, first_name, last_name, user_id, created_at, updated_at)
VALUES
    ('admin@example.com', 'Admin', 'User', 1, NOW() - INTERVAL 40 DAY, NOW() - INTERVAL 1 DAY),
    ('john.doe@example.com', 'John', 'Doe', 2, NOW() - INTERVAL 35 DAY, NOW() - INTERVAL 2 DAY),
    ('jane.smith@example.com', 'Jane', 'Smith', 3, NOW() - INTERVAL 30 DAY, NOW() - INTERVAL 3 DAY),
    ('alice.johnson@example.com', 'Alice', 'Johnson', 4, NOW() - INTERVAL 25 DAY, NOW() - INTERVAL 4 DAY),
    ('bob.brown@example.com', 'Bob', 'Brown', 5, NOW() - INTERVAL 20 DAY, NOW() - INTERVAL 5 DAY);
