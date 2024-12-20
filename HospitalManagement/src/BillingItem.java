public class BillingItem {
    private String description;
    private double amount;

    public BillingItem(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public void displayBillingItem() {
        System.out.println("Description: " + description + ", Amount: $" + amount);
    }
}