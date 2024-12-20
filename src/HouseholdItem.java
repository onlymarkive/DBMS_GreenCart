public class HouseholdItem extends Item {
    public HouseholdItem(String name, double price, int quantity) {
        super(name, "Household", price, quantity);
    }

    @Override
    public int getRewardPoints() {
        return 4; // Reward points for household items
    }

    @Override
    public String getEcoFriendlyTip() {
        return "Use energy-efficient appliances to reduce your carbon footprint!";
    }
}
