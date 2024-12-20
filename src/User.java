public class User {
    private String username;
    private double currentBudget;  // Store the user's current budget

    // Constructor to initialize username and budget
    public User(String username, double initialBudget) {
        this.username = username;
        this.currentBudget = initialBudget;
    }

    // Getter and setter for currentBudget
    public double getCurrentBudget() {
        return currentBudget;
    }

    public void setCurrentBudget(double currentBudget) {
        this.currentBudget = currentBudget;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Method to add budget
    public void addBudget(double amount) {
        this.currentBudget += amount;
    }

    // Method to subtract budget (e.g., after a purchase)
    public void subtractBudget(double amount) {
        this.currentBudget -= amount;
    }

    // Method to check if the user has sufficient funds for a purchase
    public boolean hasSufficientFunds(double amount) {
        return this.currentBudget >= amount;
    }

    // Method to display the current budget
    public void displayCurrentBudget() {
        System.out.println("Current Budget: PHP " + currentBudget);
    }
}
