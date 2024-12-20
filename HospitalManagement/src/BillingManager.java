import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BillingManager {
    private List<BillingItem> billingItems;

    public BillingManager() {
        this.billingItems = new ArrayList<>();
    }

    public void addBillingItem(BillingItem item) {
        billingItems.add(item);
    }

    public void removeBillingItem(BillingItem item) {
        billingItems.remove(item);
    }

    public List<BillingItem> getBillingItems() {
        return billingItems;
    }

    public double getTotalAmount() {
        double total = 0;
        for (BillingItem item : billingItems) {
            total += item.getAmount();
        }
        return total;
    }

    public void displayBilling() {
        System.out.println("Hospital Billing:");
        for (BillingItem item : billingItems) {
            item.displayBillingItem();
        }
        System.out.println("Total Amount: $" + getTotalAmount());
    }

    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (BillingItem item : billingItems) {
                writer.write(item.getDescription() + "," + item.getAmount());
                writer.newLine();
            }
        }
    }

    public void loadFromFile(String filename) throws IOException {
        billingItems.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String description = parts[0];
                    double amount = Double.parseDouble(parts[1]);
                    billingItems.add(new BillingItem(description, amount));
                }
            }
        }
    }
}