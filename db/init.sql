CREATE DATABASE init;

USE init;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    budget DOUBLE NOT NULL
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

SHOW TABLES;

SELECT * FROM users;
SELECT * FROM cart WHERE user_id = 1;

SHOW DATABASES;


SELECT * FROM users;

SELECT * FROM cart;

SELECT * FROM cart WHERE user_id = 1;

