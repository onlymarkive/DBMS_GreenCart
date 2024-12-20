public class User {
    private String username;
    private double currentBudget;  
    
    public User(String username, double initialBudget) {
        this.username = username;
        this.currentBudget = initialBudget;
    }

    public double getCurrentBudget() {
        return currentBudget;
    }

    public void setCurrentBudget(double currentBudget) {
        this.currentBudget = currentBudget;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addBudget(double amount) {
        this.currentBudget += amount;
    }

    public void subtractBudget(double amount) {
        this.currentBudget -= amount;
    }

    public boolean hasSufficientFunds(double amount) {
        return this.currentBudget >= amount;
    }

    public void displayCurrentBudget() {
        System.out.println("Current Budget: PHP " + currentBudget);
    }
}
