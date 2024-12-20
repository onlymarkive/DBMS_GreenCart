# GreenCartApp

**Name:** Rectin, Marielle J.  
**Section:** CS-2101  
**Course:** 211: Database Management System  

## Introduction
GreenCartApp is a Java console-based application integrated with a MySQL database to provide a seamless shopping experience. The application emphasizes eco-friendly shopping practices and promotes responsible consumption and production, aligning with Sustainable Development Goal 12.

## Description
The GreenCartApp allows users to sign up, log in, manage their budgets, and maintain a shopping cart. The app integrates MySQL for efficient data storage and retrieval, ensuring a robust and scalable solution. Users can add items to their cart, view and manage their shopping lists, and earn reward points for their purchases. Additionally, each purchase comes with an eco-friendly tip to encourage sustainable practices.

## Features
- **User Authentication**: Supports user sign-up and login functionalities with secure password storage.
- **Budget Management**: Allows users to set, view, and modify their budgets.
- **Shopping Cart Operations**: Users can add, remove, and view items in their shopping cart.
- **Reward Points System**: Earn points based on purchases, promoting sustainable shopping habits.
- **Eco-Friendly Tips**: Provides helpful suggestions to reduce environmental impact with each purchase.
- **Database Integration**: Utilizes MySQL to store user data, shopping cart details, and budgets.

## Database Schema
The project includes an `init.sql` file located in the `db` directory to initialize the database. This file contains SQL commands to:
1. Create necessary tables (e.g., users, items, cart).
2. Define keys and constraints for data integrity.
3. Insert initial data for testing purposes.

## How to Run
1. **Database Setup**:
   - Ensure MySQL is installed and running.
   - Import the `init.sql` file to initialize the database schema and data.

2. **Configure Database Connection**:
   - Update the database credentials in the `DatabaseConnection.java` file to match your local setup.

3. **Run the Application**:
   - Compile and execute the Java program.
   - Follow the prompts in the console to interact with the application.

## Additional Information
This project demonstrates the practical application of database integration in Java, showcasing essential concepts in user management, budget tracking, and shopping cart functionalities. It serves as a hands-on project for learning and applying database management skills in real-world scenarios.

For further inquiries or feedback, please contact: **Marielle J. Rectin**.
