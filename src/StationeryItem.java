public class StationeryItem extends Item {
    public StationeryItem(String name, double price, int quantity) {
        super(name, "Stationery", price, quantity);
    }

    @Override
    public int getRewardPoints() {
        return 1; // Reward points for stationery items
    }

    @Override
    public String getEcoFriendlyTip() {
        return "Choose stationery made from recycled paper and materials!";
    }
}
