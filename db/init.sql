CREATE DATABASE init;

USE init;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    budget DOUBLE NOT NULL DEFAULT 1000
);

CREATE TABLE cart (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    item_name VARCHAR(100) NOT NULL,
    item_type VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,
    total_cost DOUBLE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO users (username, password) VALUES 
('mawrkive', 'password123'),
('mawee', 'password456'),
('rkive', 'password789');

INSERT INTO cart (user_id, item_name, item_type, quantity, price, total_cost) VALUES
(1, 'Milk', 'Grocery', 4, 75.00, 300.00),
(1, 'T-shirt', 'Clothing', 1, 350.00, 350.00),
(2, 'Notebook', 'Stationery', 3, 50.00, 150.00),
(3, 'Charger', 'Electronics', 1, 350.00, 350.00);

SHOW TABLES;

SELECT * FROM users;

SELECT * FROM cart WHERE user_id = 1;

SHOW DATABASES;

SELECT * FROM users;

SELECT * FROM cart;

SELECT * FROM cart WHERE user_id = 1;
