public class ClothingItem extends Item {

    // Constructor for creating a ClothingItem
    public ClothingItem(String name, double price, int quantity) {
        // Call the parent constructor (Item) to initialize common properties
        super(name, "Clothing", price, quantity);
    }

    // Override getRewardPoints method to return reward points for clothing items
    @Override
    public int getRewardPoints() {
        // Clothing items earn 2 reward points per item
        return 2;
    }

    // Override getEcoFriendlyTip method to provide an eco-friendly tip for clothing
    @Override
    public String getEcoFriendlyTip() {
        // Provide a tip to encourage buying sustainable clothing
        return "Buy clothes made from sustainable materials!";
    }

    // Optional: Override toString() for a custom string representation
    @Override
    public String toString() {
        return super.toString() + " | Eco-Friendly Tip: " + getEcoFriendlyTip();
    }
}
