import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BillingManagerGUI extends JFrame {
    private BillingManager billingManager;
    private JTextArea billingTextArea;
    private JTextField descriptionField;
    private JTextField amountField;

    public BillingManagerGUI() {
        billingManager = new BillingManager();
        setTitle("Hospital Billing Manager");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        billingTextArea = new JTextArea();
        billingTextArea.setEditable(false);
        add(new JScrollPane(billingTextArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        inputPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        inputPanel.add(amountField);

        JButton addButton = new JButton("Add Billing Item");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBillingItem();
            }
        });
        inputPanel.add(addButton);

        JButton displayButton = new JButton("Display Billing");
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayBilling();
            }
        });
        inputPanel.add(displayButton);

        add(inputPanel, BorderLayout.SOUTH);
    }

    private void addBillingItem() {
        String description = descriptionField.getText();
        double amount = Double.parseDouble(amountField.getText());
        BillingItem item = new BillingItem(description, amount);
        billingManager.addBillingItem(item);
        descriptionField.setText("");
        amountField.setText("");
    }

    private void displayBilling() {
        billingTextArea.setText("");
        for (BillingItem item : billingManager.getBillingItems()) {
            billingTextArea.append(item.getDescription() + ": $" + item.getAmount() + "\n");
        }
        billingTextArea.append("Total Amount: $" + billingManager.getTotalAmount() + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BillingManagerGUI().setVisible(true);
            }
        });
    }
}
