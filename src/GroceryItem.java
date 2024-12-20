public class GroceryItem extends Item {
    public GroceryItem(String name, double price, int quantity) {
        super(name, "Grocery", price, quantity);
    }

    @Override
    public int getRewardPoints() {
        return 3; // Reward points for grocery items
    }

    @Override
    public String getEcoFriendlyTip() {
        return "Consider buying eco-friendly products that help reduce waste!";
    }
}
