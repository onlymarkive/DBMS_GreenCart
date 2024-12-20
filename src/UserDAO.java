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

        String insertQuery = "INSERT INTO users (username, password, budget) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertQuery)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setBigDecimal(3, new BigDecimal(1000)); 
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    public boolean loginUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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
        return 0;  
    }

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

    public boolean addItemToCart(String username, String itemName, String itemType, int quantity, double price) {
        double totalCost = price * quantity;
        double currentBudget = getUserBudget(username);  
        
        if (currentBudget < totalCost) {
            System.out.println("Error: Insufficient budget to add this item to the cart.");
            return false;
        }
    
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
                double newBudget = currentBudget - totalCost;
                setUserBudget(username, newBudget); 
    
                System.out.println("\nItem added successfully!");
                System.out.println("Total Cost: PHP " + totalCost);
                System.out.println("Remaining Budget: PHP " + newBudget);
    
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
    
    public boolean removeItemFromCart(String username, String itemName) {
        String query = "DELETE FROM cart WHERE user_id = (SELECT id FROM users WHERE username = ?) AND item_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, itemName);
            int rows = stmt.executeUpdate();
            return rows > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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
        
            double currentBudget = getUserBudget(username);  
        
            System.out.println("Grand Total (PHP): " + grandTotal);
            System.out.println("Remaining Budget (PHP): " + currentBudget);  
            System.out.println("Total Reward Points: " + totalRewardPoints);
            System.out.println("----------------------------");
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }              
    
    private int calculateRewardPoints(String itemType, int quantity) {
        int rewardPoints = 0;
        
        switch (itemType.toLowerCase()) {
            case "grocery":
                rewardPoints = quantity * 1; 
                break;
            case "electronic":
                rewardPoints = quantity * 2; 
                break;
            case "clothing":
                rewardPoints = quantity * 3; 
                break;
            case "household":
                rewardPoints = quantity * 2; 
                break;
            case "stationery":
                rewardPoints = quantity * 1; 
                break;
            default:
                rewardPoints = 0;
        }
    
        return rewardPoints;
    }
    
}
