import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {
    public Dashboard() {
        setTitle("Hospital Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Custom panel with background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("Ngojomedicalhospitaldashboard.png");
                Image image = icon.getImage();
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JButton doctorManagementButton = new JButton("Doctor Management");
        JButton patientManagementButton = new JButton("Patient Management");
        JButton appointmentManagementButton = new JButton("Appointment Management");
        JButton logoutButton = new JButton("Logout");

        // Set button colors and sizes
        doctorManagementButton.setBackground(new Color(34, 139, 34)); // Forest Green
        doctorManagementButton.setForeground(Color.WHITE);
        doctorManagementButton.setPreferredSize(new Dimension(200, 50));

        patientManagementButton.setBackground(new Color(70, 130, 180)); // Steel Blue
        patientManagementButton.setForeground(Color.WHITE);
        patientManagementButton.setPreferredSize(new Dimension(200, 50));

        appointmentManagementButton.setBackground(new Color(255, 140, 0)); // Dark Orange
        appointmentManagementButton.setForeground(Color.WHITE);
        appointmentManagementButton.setPreferredSize(new Dimension(200, 50));

        logoutButton.setBackground(new Color(178, 34, 34)); // Firebrick
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setPreferredSize(new Dimension(200, 50));

        buttonPanel.add(doctorManagementButton);
        buttonPanel.add(patientManagementButton);
        buttonPanel.add(appointmentManagementButton);
        buttonPanel.add(logoutButton);

        backgroundPanel.add(buttonPanel, BorderLayout.NORTH);
        add(backgroundPanel, BorderLayout.CENTER);

        // Button actions
        doctorManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DoctorManagement();
            }
        });

        patientManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PatientManagement();
            }
        });

        appointmentManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AppointmentManagement();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginScreen();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Dashboard::new);
    }
}