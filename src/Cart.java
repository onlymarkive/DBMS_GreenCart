import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Item> items;
    private UserDAO userDAO;

    public Cart(UserDAO userDAO) {
        this.userDAO = userDAO;  // Initialize UserDAO to get the user's budget
        this.items = new ArrayList<>();
    }

    public double getRemainingBudget(String username) {
        // Get the user's current budget from the UserDAO
        return userDAO.getUserBudget(username);  // No need to recalculate here
    }

    public void addItem(String username, String name, double price, int quantity, int type) {
        // Get the current remaining budget only once
        double remainingBudget = userDAO.getUserBudget(username);
        double totalItemCost = price * quantity;
    
        // Check if the user has enough budget
        if (remainingBudget >= totalItemCost) {
            // Deduct the cost from the user's budget
            double updatedBudget = remainingBudget - totalItemCost;
    
            // Add item to the cart (in-memory)
            Item newItem = new Item(username, name, price, quantity, type);
            items.add(newItem);
    
            // Update the database with the new item
            if (!userDAO.addItemToCart(username, name, "Type" + type, quantity, price)) {
                System.out.println("Error: Failed to add item to cart.");
                return;
            }
    
            // Update the user's budget in the database only once after adding the item
            userDAO.setUserBudget(username, updatedBudget);
            
            // Confirm item addition and display updated budget
            System.out.println("Item added successfully!");
            System.out.println("Total Cost: PHP " + totalItemCost);
            System.out.println("Remaining Budget: PHP " + updatedBudget);
        } else {
            // If the user doesn't have enough budget
            System.out.println("Error: Insufficient budget to add this item.");
        }
    }        

    public void removeItem(String username, String itemName) {
        // Remove the item from the cart (in-memory)
        Item itemToRemove = null;
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                itemToRemove = item;
                break;
            }
        }

        if (itemToRemove != null) {
            // Calculate the cost of the removed item
            double totalItemCost = itemToRemove.calculateTotalCost();

            // Remove item from the database
            if (!userDAO.removeItemFromCart(username, itemName)) {
                System.out.println("Error: Failed to remove item from cart.");
                return;
            }

            // Update the user's budget by adding back the cost of the removed item
            double currentBudget = userDAO.getUserBudget(username);
            double updatedBudget = currentBudget + totalItemCost;  // Add back the removed item's cost
            userDAO.setUserBudget(username, updatedBudget);

            // Remove the item from the in-memory list
            items.remove(itemToRemove);
            System.out.println("Item removed successfully!");

            // Print updated information
            System.out.println("Total Cost of Removed Item: PHP " + totalItemCost);
            System.out.println("Updated Remaining Budget: PHP " + updatedBudget);
        } else {
            System.out.println("Item not found in the cart.");
        }
    }

    public void viewItems(String username) {
        // View all items in the cart
        items.forEach(item -> {
            System.out.println("Item Name: " + item.getName());
            System.out.println("Item Type: " + item.getItemType());
            System.out.println("Quantity: " + item.getQuantity());
            System.out.println("Price (PHP): " + item.getPrice());
            System.out.println("Total Cost (PHP): " + item.calculateTotalCost());
            System.out.println("----------------------------");
        });

        // Get the updated remaining budget directly from the database
        double remainingBudget = userDAO.getUserBudget(username);

        // Calculate the grand total and reward points
        double grandTotal = calculateTotalCost();
        int totalRewardPoints = calculateRewardPoints();

        // Display a warning if the grand total exceeds the budget
        if (grandTotal > remainingBudget) {
            System.out.println("Warning: Your grand total exceeds your budget!");
            remainingBudget = 0;  // Set remaining budget to 0 if it exceeds the grand total
        }

        // Display grand total, total reward points, and remaining budget
        System.out.println("----------------------------");
        System.out.println("Grand Total: PHP " + grandTotal);
        System.out.println("Total Reward Points Earned: " + totalRewardPoints);
        System.out.println("Remaining Budget: PHP " + remainingBudget);  // Just fetch the correct budget from the database
    }

    // Calculate total cost of items in the cart
    public double calculateTotalCost() {
        return items.stream()
                    .mapToDouble(Item::calculateTotalCost)
                    .sum();
    }

    public int calculateRewardPoints() {
        return items.stream()
                    .mapToInt(Item::calculateRewardPoints)
                    .sum();
    }

    public String getEcoFriendlyTip() {
        return "Consider buying eco-friendly products that help reduce waste!";
    }

    public void addBudget(String username, double amount) {
        // Update the user's budget through the UserDAO
        double currentBudget = userDAO.getUserBudget(username);
        double updatedBudget = currentBudget + amount;
        userDAO.setUserBudget(username, updatedBudget);  // Assuming setUserBudget updates the budget in the database
    }
}
