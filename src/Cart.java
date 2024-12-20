import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Item> items;
    private UserDAO userDAO;

    public Cart(UserDAO userDAO) {
        this.userDAO = userDAO;  
        this.items = new ArrayList<>();
    }

    public double getRemainingBudget(String username) {
        return userDAO.getUserBudget(username);  
    }

    public void addItem(String username, String name, double price, int quantity, int type) {
        double remainingBudget = userDAO.getUserBudget(username);
        double totalItemCost = price * quantity;
    
        if (remainingBudget >= totalItemCost) {
            double updatedBudget = remainingBudget - totalItemCost;
    
            Item newItem = new Item(username, name, price, quantity, type);
            items.add(newItem);
    
            if (!userDAO.addItemToCart(username, name, "Type" + type, quantity, price)) {
                System.out.println("Error: Failed to add item to cart.");
                return;
            }
    
            userDAO.setUserBudget(username, updatedBudget);
            
            System.out.println("Item added successfully!");
            System.out.println("Total Cost: PHP " + totalItemCost);
            System.out.println("Remaining Budget: PHP " + updatedBudget);
        } else {
            System.out.println("Error: Insufficient budget to add this item.");
        }
    }        

    public void removeItem(String username, String itemName) {
        Item itemToRemove = null;
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                itemToRemove = item;
                break;
            }
        }

        if (itemToRemove != null) {
            double totalItemCost = itemToRemove.calculateTotalCost();

            if (!userDAO.removeItemFromCart(username, itemName)) {
                System.out.println("Error: Failed to remove item from cart.");
                return;
            }

            double currentBudget = userDAO.getUserBudget(username);
            double updatedBudget = currentBudget + totalItemCost;  
            userDAO.setUserBudget(username, updatedBudget);

            items.remove(itemToRemove);
            System.out.println("Item removed successfully!");

            System.out.println("Total Cost of Removed Item: PHP " + totalItemCost);
            System.out.println("Updated Remaining Budget: PHP " + updatedBudget);
        } else {
            System.out.println("Item not found in the cart.");
        }
    }

    public void viewItems(String username) {
        items.forEach(item -> {
            System.out.println("Item Name: " + item.getName());
            System.out.println("Item Type: " + item.getItemType());
            System.out.println("Quantity: " + item.getQuantity());
            System.out.println("Price (PHP): " + item.getPrice());
            System.out.println("Total Cost (PHP): " + item.calculateTotalCost());
            System.out.println("----------------------------");
        });

        double remainingBudget = userDAO.getUserBudget(username);

        double grandTotal = calculateTotalCost();
        int totalRewardPoints = calculateRewardPoints();

        if (grandTotal > remainingBudget) {
            System.out.println("Warning: Your grand total exceeds your budget!");
            remainingBudget = 0;  
        }

        System.out.println("----------------------------");
        System.out.println("Grand Total: PHP " + grandTotal);
        System.out.println("Total Reward Points Earned: " + totalRewardPoints);
        System.out.println("Remaining Budget: PHP " + remainingBudget);  
    }

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
        double currentBudget = userDAO.getUserBudget(username);
        double updatedBudget = currentBudget + amount;
        userDAO.setUserBudget(username, updatedBudget);  
    }
}
