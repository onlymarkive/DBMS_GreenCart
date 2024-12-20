public abstract class Item {
    private String name;
    private String type;
    private double price;
    private int quantity;

    // Constructor for initializing an Item object
    public Item(String name, String type, double price, int quantity) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters for the fields
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    // Abstract methods that must be implemented by subclasses
    public abstract int getRewardPoints();
    public abstract String getEcoFriendlyTip();

    // Optional: Override toString() for a custom representation of the item
    @Override
    public String toString() {
        return "Item Name: " + name + " | Type: " + type + " | Price: " + price + " | Quantity: " + quantity;
    }
}
