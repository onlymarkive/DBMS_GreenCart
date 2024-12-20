import java.math.BigDecimal;
import java.sql.*;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String username, String password) {
        // Check if username already exists
        String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Username already exists. Try again.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error checking username: " + e.getMessage());
            return false;
        }

        // Insert new user with default budget (1000)
        String insertQuery = "INSERT INTO users (username, password, budget) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertQuery)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setBigDecimal(3, new BigDecimal(1000)); // Default budget
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
            return false;
        }
    }


    // Log in user
    public boolean loginUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // If a user is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get the user's current budget
    public double getUserBudget(String username) {
        String query = "SELECT budget FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("budget");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;  // Return 0 if no budget set
    }

    // Set user's budget
    public void setUserBudget(String username, double newBudget) {
        String query = "UPDATE users SET budget = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, newBudget);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add an item to the user's cart
    public boolean addItemToCart(String username, String itemName, String itemType, int quantity, double price) {
        double totalCost = price * quantity;
        double currentBudget = getUserBudget(username);  // Get current budget
        
        if (currentBudget < totalCost) {
            // Prevent adding the item if the user doesn't have enough budget
            System.out.println("Error: Insufficient budget to add this item to the cart.");
            return false;
        }
    
        // Perform the actual insertion of the item into the cart
        String query = "INSERT INTO cart (user_id, item_name, item_type, quantity, price, total_cost) " +
                       "SELECT id, ?, ?, ?, ?, ? FROM users WHERE username = ?";
    
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, itemName);
            stmt.setString(2, itemType);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);
            stmt.setDouble(5, totalCost);
            stmt.setString(6, username);
            int rows = stmt.executeUpdate();
    
            if (rows > 0) {
                // Update the user's budget only after successful insertion
                double newBudget = currentBudget - totalCost;
                setUserBudget(username, newBudget); // Update the budget
                System.out.println("Item added successfully!");
                System.out.println("Total Cost: PHP " + totalCost);
                System.out.println("Remaining Budget: PHP " + newBudget);
                // Assuming you calculate reward points in another method
                int rewardPoints = calculateRewardPoints(itemType, quantity);
                System.out.println("Reward Points Earned: " + rewardPoints);
                System.out.println("Eco-Friendly Tip: Consider buying eco-friendly products that help reduce waste!");
                return true;
            } else {
                System.out.println("Error: Failed to add item to cart.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Remove an item from the user's cart
    public boolean removeItemFromCart(String username, String itemName) {
        String query = "DELETE FROM cart WHERE user_id = (SELECT id FROM users WHERE username = ?) AND item_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, itemName);
            int rows = stmt.executeUpdate();
            return rows > 0; // Return true if rows were affected (item removed)
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // View the user's shopping list (cart)
    public void viewShoppingList(String username) {
        String query = "SELECT item_name, item_type, quantity, price, total_cost FROM cart " +
                       "WHERE user_id = (SELECT id FROM users WHERE username = ?)";
        
        double grandTotal = 0.0;
        int totalRewardPoints = 0;
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
        
            System.out.println("Shopping List:");
            while (rs.next()) {
                String itemName = rs.getString("item_name");
                String itemType = rs.getString("item_type");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                double totalCost = rs.getDouble("total_cost");
        
                System.out.println("Item Name: " + itemName);
                System.out.println("Item Type: " + itemType);
                System.out.println("Quantity: " + quantity);
                System.out.println("Price (PHP): " + price);
                System.out.println("Total Cost (PHP): " + totalCost);
                System.out.println("----------------------------");
        
                grandTotal += totalCost;
                totalRewardPoints += calculateRewardPoints(itemType, quantity);
            }
        
            // Get the current budget (no need to subtract grand total again)
            double currentBudget = getUserBudget(username);  // Get current budget from database
        
            // Directly display the current remaining budget
            System.out.println("Grand Total (PHP): " + grandTotal);
            System.out.println("Remaining Budget (PHP): " + currentBudget);  // Display the correct remaining budget
            System.out.println("Total Reward Points: " + totalRewardPoints);
            System.out.println("----------------------------");
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }              
    
    // Assuming a method for calculating reward points based on the item type and quantity
    private int calculateRewardPoints(String itemType, int quantity) {
        int rewardPoints = 0;
        
        // Assign reward points based on item type
        switch (itemType.toLowerCase()) {
            case "grocery":
                rewardPoints = quantity * 1; // 1 point per item
                break;
            case "electronic":
                rewardPoints = quantity * 2; // 2 points per item
                break;
            case "clothing":
                rewardPoints = quantity * 3; // 3 points per item
                break;
            case "household":
                rewardPoints = quantity * 2; // 1 point per item
                break;
            case "stationery":
                rewardPoints = quantity * 1; // 1 point per item
                break;
            default:
                rewardPoints = 0;
        }
    
        return rewardPoints;
    }
    
}
