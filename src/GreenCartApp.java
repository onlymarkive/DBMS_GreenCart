import java.util.Scanner;

public class GreenCartApp {
    private static Scanner scanner = new Scanner(System.in);
    private static UserDAO userDAO = new UserDAO();
    private static String loggedInUsername = null;

    public static void main(String[] args) {
        System.out.println("Welcome to GreenCart!");
        while (true) {
            System.out.println("\n1. Sign Up\n2. Log In\n3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    signUp();
                    break;
                case 2:
                    logIn();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    // Sign Up Method
    private static void signUp() {
        System.out.println("\n--- Sign Up ---");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (userDAO.registerUser(username, password)) {
            System.out.println("Registration successful! Please log in.");
        } else {
            System.out.println("Username already exists. Try again.");
        }
    }

    // Log In Method
    private static void logIn() {
        System.out.println("\n--- Log In ---");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (userDAO.loginUser(username, password)) {
            System.out.println("Login successful! Welcome, " + username);
            loggedInUsername = username;

            // Check if the budget is already set, only ask for it on first login
            double currentBudget = userDAO.getUserBudget(username);
            if (currentBudget == 0) {  // If no budget is set (first login)
                System.out.print("Set your initial Budget: PHP ");
                double newBudget = scanner.nextDouble();
                userDAO.setUserBudget(username, newBudget);
            } else {
                System.out.println("Your current remaining budget: PHP " + currentBudget);
            }

            userMenu();
        } else {
            System.out.println("Invalid username or password. Try again.");
        }
    }

    // User Menu after logging in
    private static void userMenu() {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Add an Item to Cart\n2. Remove an Item from Cart\n3. View Shopping List\n4. Add Budget\n5. Log Out");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addItemToCart();
                    break;
                case 2:
                    removeItemFromCart();
                    break;
                case 3:
                    viewShoppingList();
                    break;
                case 4:
                    addBudget();
                    break;
                case 5:
                    logOut();
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    // Add Item to Cart
    private static void addItemToCart() {
        System.out.println("\n--- Add an Item ---");
        System.out.println("Choose item type:");
        System.out.println("1. Grocery\n2. Electronic\n3. Clothing\n4. Household\n5. Stationery");
        System.out.print("Enter your choice (1-5): ");
        int itemTypeChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();

        System.out.print("Enter item price (PHP): ");
        double itemPrice = scanner.nextDouble();

        System.out.print("Enter item quantity: ");
        int itemQuantity = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String itemType = getItemType(itemTypeChoice);
        double totalCost = itemPrice * itemQuantity;

        // Get the current budget before adding the item
        double currentBudget = userDAO.getUserBudget(loggedInUsername);

        if (currentBudget >= totalCost) { // Check if the user has enough budget
            // Attempt to add the item to the cart
            boolean isAdded = userDAO.addItemToCart(loggedInUsername, itemName, itemType, itemQuantity, itemPrice);

            if (isAdded) {
                // Update budget after successfully adding item to cart
                double remainingBudget = currentBudget - totalCost;
                userDAO.setUserBudget(loggedInUsername, remainingBudget);

                // Display success message once
                System.out.println("\nItem added successfully!");
                System.out.println("Total Cost: PHP " + totalCost);
                System.out.println("Remaining Budget: PHP " + remainingBudget);
                System.out.println("Reward Points Earned: " + (int) (totalCost / 100)); // Assume 1 point per 100 PHP
                System.out.println("Eco-Friendly Tip: Consider buying eco-friendly products that help reduce waste!");
            } else {
                System.out.println("Failed to add item to cart.");
            }
        } else {
            System.out.println("Insufficient budget to add this item.");
        }
    }

    private static String getItemType(int choice) {
        switch (choice) {
            case 1: return "Grocery";
            case 2: return "Electronic";
            case 3: return "Clothing";
            case 4: return "Household";
            case 5: return "Stationery";
            default: return "Unknown";
        }
    }

    // View Shopping List
    private static void viewShoppingList() {
        System.out.println("\n--- Shopping List ---");
        userDAO.viewShoppingList(loggedInUsername);
    }

    // Add Budget
    private static void addBudget() {
        System.out.print("Enter amount to add to your budget: PHP ");
        double amount = scanner.nextDouble();
        double newBudget = userDAO.getUserBudget(loggedInUsername) + amount;
        userDAO.setUserBudget(loggedInUsername, newBudget);
        System.out.println("New Budget: PHP " + newBudget);
    }

    // Log Out
    private static void logOut() {
        loggedInUsername = null;
        System.out.println("Logged out successfully!");
    }

    // Remove Item from Cart
    private static void removeItemFromCart() {
        System.out.println("Enter item name to remove:");
        String itemName = scanner.nextLine();
        if (userDAO.removeItemFromCart(loggedInUsername, itemName)) {
            double remainingBudget = userDAO.getUserBudget(loggedInUsername);
            System.out.println("Item removed successfully!");
            System.out.println("Remaining Budget: PHP " + remainingBudget);
        } else {
            System.out.println("Item not found in cart.");
        }
    }
}
