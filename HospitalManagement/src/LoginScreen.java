import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.prefs.Preferences;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Preferences prefs;

    public LoginScreen() {
        prefs = Preferences.userRoot().node(this.getClass().getName());

        setTitle("Login");
        setPreferredSize(new Dimension(600, 450)); // Set preferred size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for hospital name and logo
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(new Color(173, 216, 230)); // Light blue background color

        // Add logo
        ImageIcon logoIcon = new ImageIcon("ngojomedicalhospitalfinal.png"); // Replace with the path to your logo image
        Image logoImage = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Scale the logo
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));

        JLabel hospitalNameLabel = new JLabel("Ngojo Medical Hospital");
        hospitalNameLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Adjust font size
        hospitalNameLabel.setForeground(new Color(0, 102, 204)); // Change font color

        topPanel.add(logoLabel);
        topPanel.add(hospitalNameLabel);

        // Center panel for login fields with padding and background image
        JPanel centerPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundIcon = new ImageIcon("hospitalbuilding.jpg"); // Replace with the path to your image
                Image backgroundImage = backgroundIcon.getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g.setColor(new Color(240, 248, 255, 150)); // Light blue semi-transparent color
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        centerPanel.setBackground(new Color(240, 248, 255, 150)); // Light blue semi-transparent background color
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        usernameField = new JTextField(15);
        usernameField.setText(prefs.get("username", "")); // Retrieve saved username
        usernameField.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent background
        usernameField.setOpaque(true); // Ensure the background is painted
        centerPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        passwordField = new JPasswordField(15);
        passwordField.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent background
        passwordField.setOpaque(true); // Ensure the background is painted
        centerPanel.add(passwordField, gbc);

        // Panel for checkboxes
        JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkboxPanel.setBackground(new Color(240, 248, 255)); // Light blue background color

        // Show/Hide Password Checkbox
        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.setBackground(new Color(240, 248, 255)); // Light blue background color
        showPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPassword.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                }
            }
        });
        checkboxPanel.add(showPassword);

        // Remember Me Checkbox
        JCheckBox rememberMe = new JCheckBox("Remember Me");
        rememberMe.setBackground(new Color(240, 248, 255)); // Light blue background color
        checkboxPanel.add(rememberMe);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(checkboxPanel, gbc);

        // Login Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(144, 238, 144)); // Light green background color
        loginButton.setForeground(Color.BLACK); // Set text color
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin(rememberMe.isSelected());
            }
        });
        centerPanel.add(loginButton, gbc);

        // Reset Button
        gbc.gridy = 4;
        JButton resetButton = new JButton("Reset");
        resetButton.setBackground(new Color(255, 182, 193)); // Light pink background color
        resetButton.setForeground(Color.BLACK); // Set text color
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                passwordField.setText("");
            }
        });
        centerPanel.add(resetButton, gbc);

        // Forgot Password Link
        gbc.gridy = 5;
        JLabel forgotPassword = new JLabel("<HTML><U>Forgot Password?</U></HTML>");
        forgotPassword.setForeground(Color.BLUE);
        forgotPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                new ResetPasswordDialog(LoginScreen.this).setVisible(true);
            }
        });
        centerPanel.add(forgotPassword, gbc);

        // Sign Up Link
        gbc.gridy = 6;
        JLabel signUp = new JLabel("<HTML><U>Sign Up</U></HTML>");
        signUp.setForeground(Color.BLUE);
        signUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JOptionPane.showMessageDialog(null, "Contact the Ngojo Medical Hospital IT Team for assistance!.");
            }
        });
        centerPanel.add(signUp, gbc);

        // Footer panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(173, 216, 230)); // Light blue background color
        JLabel footerLabel = new JLabel("© 2023 Ngojo Medical Hospital");
        footerPanel.add(footerLabel);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        pack(); // Adjust the frame size to fit its components
        setVisible(true);
    }

    private void handleLogin(boolean rememberMe) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Validate fields
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Simple authentication check (replace with real authentication logic)
        if (username.equals("admin") && password.equals("password")) {
            if (rememberMe) {
                prefs.put("username", username); // Save username
            } else {
                prefs.remove("username");
            }
            dispose();
            new Dashboard();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}