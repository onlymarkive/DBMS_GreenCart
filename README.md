# 🌿 GreenCart Application

**Developed by:** Rectin, Marielle J.  
**Section:** CS-2101  
**Course:** 211: DATABASE MANAGEMENT SYSTEM

---

## 📖 Introduction
GreenCart is a Java-based console application with MySQL database integration, designed to promote eco-friendly shopping. The application allows users to manage their shopping experience responsibly by setting budgets, earning rewards, and receiving eco-friendly tips. GreenCart aligns with sustainable development principles by encouraging environmentally conscious choices.

---

## ✨ Features
- **👤 User Account Management**: Users can sign up, log in, and manage their accounts securely.
- **💳 Budget Management**: Users can set, view, and modify their budgets for shopping.
- **🛒 Shopping Cart Operations**:
  - Add items to the cart with type, price, and quantity.
  - Remove items from the cart.
  - View a detailed shopping list with totals.
- **🎁 Reward System**: Earn points based on purchases, encouraging sustainable shopping habits.
- **🌱 Eco-Friendly Tips**: Receive helpful suggestions to reduce waste and shop sustainably.

---

## 🛠️ Setup Instructions

### Prerequisites
- **Java Development Kit (JDK)**
- **MySQL Server**
- **Git**

### Steps
1. Clone the repository from GitHub:
   ```bash
   git clone <repository-url>
   ```
2. Navigate to the project directory and locate the `db` folder.
3. Import the `init.sql` file into your MySQL database to create the required schema and initial data:
   ```bash
   mysql -u <username> -p < database_name < db/init.sql
   ```
4. Configure the database connection in the `DatabaseConnection.java` file.
5. Compile and run the Java application:
   ```bash
   javac GreenCartApp.java
   java GreenCartApp
   ```

---

## 📂 Directory Structure
- **/src**: Contains all source code files.
- **/db**: Includes `init.sql` to initialize the database schema and data.

---

## 💡 How It Works
1. **Sign Up & Log In**: Users can register an account and log in to access personalized shopping features.
2. **Budget Management**: Users can set an initial budget and modify it as needed.
3. **Shopping**:
   - Add items to the cart by specifying type, name, price, and quantity.
   - View the shopping list with details of items, total cost, and remaining budget.
   - Remove items from the cart if needed.
4. **Reward Points**: Earn points based on purchases and see them reflected in the shopping list.
5. **Eco-Friendly Tips**: Receive actionable advice with each shopping interaction to promote sustainability.

---

## 🚀 Future Enhancements
- **📊 Detailed Analytics**: Provide users with insights into their shopping habits and spending trends.
- **📱 Mobile Integration**: Expand to a mobile-friendly interface.
- **🌍 Advanced Eco-Tips**: Offer more dynamic and personalized sustainability tips.

---

## 🤝 Contribution
Contributions are welcome! Feel free to fork the repository, create a feature branch, and submit a pull request.

---

Enjoy a sustainable shopping experience with 🌿 GreenCart!
