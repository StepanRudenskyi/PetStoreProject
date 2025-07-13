-- Insert users (with timestamps)
INSERT INTO user (username, password, created_at, updated_at)
VALUES
    ('admin', '$2a$10$M2yzYkziVXElZKNmlgkgAuCxA.FfZJn7wNvH7ZL7BlmTja0jRjVMW', NOW() - INTERVAL 40 DAY, NOW() - INTERVAL 1 DAY),
    ('john.doe', '$2a$10$8VDGUoiB8N4HgxINdF2PYOmI7pXy8WfLkfVsnScbo/chpdku4JG66', NOW() - INTERVAL 35 DAY, NOW() - INTERVAL 2 DAY),
    ('user', '$2a$10$0/Sd0HzjG2yD7pnokE5tRe3WAkudNqErMsq.jNxgRdFb8g9MPaOuK', NOW() - INTERVAL 30 DAY, NOW() - INTERVAL 3 DAY),
    ('alice.johnson', '$2a$10$1cgNDw8vYzuP5skoxa0cvODfO7nRih5m8kkJygv43o/RRAqYklcnK', NOW() - INTERVAL 25 DAY, NOW() - INTERVAL 4 DAY),
    ('bob.brown', '$2a$10$qBR0iSJwcOKYILZSrAJHO.nyZviGVgr4bFHHBx5aUb7zCmFj0/AkK', NOW() - INTERVAL 20 DAY, NOW() - INTERVAL 5 DAY);

INSERT INTO user_roles (user_id, role)
VALUES
    (1, 'ADMIN'),
    (2, 'USER'),
    (3, 'USER'),
    (4, 'USER'),
    (5, 'USER');