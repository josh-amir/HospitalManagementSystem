import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResetPasswordDialog extends JDialog {
    private JTextField emailField;

    public ResetPasswordDialog(JFrame parent) {
        super(parent, "Reset Password", true);
        setLayout(new BorderLayout());

        // Center panel for email input
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("Enter your email address:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        centerPanel.add(emailField, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton resetButton = new JButton("Reset Password");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleResetPassword();
            }
        });
        buttonPanel.add(resetButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    private void handleResetPassword() {
        String email = emailField.getText();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email address cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Simulate sending a password reset link
        JOptionPane.showMessageDialog(this, "A password reset link has been sent to " + email, "Reset Password", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}