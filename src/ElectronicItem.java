public class ElectronicItem extends Item {
    public ElectronicItem(String name, double price, int quantity) {
        super(name, "Electronic", price, quantity);
    }

    @Override
    public int getRewardPoints() {
        return 5; // Reward points for electronic items
    }

    @Override
    public String getEcoFriendlyTip() {
        return "Recycle your electronics to avoid e-waste!";
    }
}
