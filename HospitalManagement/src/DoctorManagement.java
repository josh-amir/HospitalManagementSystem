import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class DoctorManagement extends JFrame {
    private JTable doctorTable;
    private DefaultTableModel tableModel;
    private static final String FILE_NAME = "doctors_data.txt";
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> rowSorter;

    public DoctorManagement() {
        setTitle("Doctor Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Main panel with BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);

        // Logo panel
        JPanel logoPanel = new JPanel();
        ImageIcon logoIcon = new ImageIcon("ngojomedoctorsfinal.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoPanel.add(logoLabel);
        mainPanel.add(logoPanel);

        // Panel for search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        mainPanel.add(searchPanel);

        // Table to display doctor information
        String[] columnNames = {"ID", "Name", "Specialization", "Contact", "Gender", "Experience", "Availability"};
        tableModel = new DefaultTableModel(columnNames, 0);
        doctorTable = new JTable(tableModel);
        rowSorter = new TableRowSorter<>(tableModel);
        doctorTable.setRowSorter(rowSorter);
        JScrollPane scrollPane = new JScrollPane(doctorTable);
        mainPanel.add(scrollPane);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JButton addButton = new JButton("Add Doctor");
        JButton editButton = new JButton("Edit Doctor");
        JButton deleteButton = new JButton("Delete Doctor");
        JButton backButton = new JButton("Back");

        // Set button colors and sizes for better visibility
        addButton.setBackground(new Color(34, 139, 34)); // Forest Green
        addButton.setForeground(Color.WHITE);
        addButton.setPreferredSize(new Dimension(150, 40));

        editButton.setBackground(new Color(255, 140, 0)); // Dark Orange
        editButton.setForeground(Color.WHITE);
        editButton.setPreferredSize(new Dimension(150, 40));

        deleteButton.setBackground(new Color(178, 34, 34)); // Firebrick
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setPreferredSize(new Dimension(150, 40));

        backButton.setBackground(new Color(70, 130, 180)); // Steel Blue
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(150, 40));

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add Doctor button action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDoctor();
            }
        });

        // Edit Doctor button action
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editDoctor();
            }
        });

        // Delete Doctor button action
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteDoctor();
            }
        });

        // Search button action
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        // Back button action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                new Dashboard(); // Open the previous dashboard
            }
        });

        // Load data from file
        loadDataFromFile();

        setVisible(true);
    }

    private void addDoctor() {
        JTextField idField = new JTextField(5);
        JTextField nameField = new JTextField(15);
        JTextField specializationField = new JTextField(15);
        JTextField contactField = new JTextField(10);
        JComboBox<String> genderField = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        JComboBox<String> experienceField = new JComboBox<>(new String[]{"0-10", "10-20", "20-30", "30+"});
        JTextField availabilityField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));

        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Specialization:"));
        panel.add(specializationField);
        panel.add(new JLabel("Contact:"));
        panel.add(contactField);
        panel.add(new JLabel("Gender:"));
        panel.add(genderField);
        panel.add(new JLabel("Experience:"));
        panel.add(experienceField);
        panel.add(new JLabel("Availability:"));
        panel.add(availabilityField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Doctor", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            String name = nameField.getText();
            String specialization = specializationField.getText();
            String contact = contactField.getText();
            String gender = (String) genderField.getSelectedItem();
            String experience = (String) experienceField.getSelectedItem();
            String availability = availabilityField.getText();
            tableModel.addRow(new Object[]{id, name, specialization, contact, gender, experience, availability});
            saveDataToFile();
            rowSorter.setRowFilter(null); // Reset the row filter to show all rows
        }
    }

    private void editDoctor() {
        int selectedRow = doctorTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a doctor to edit", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = (String) tableModel.getValueAt(selectedRow, 0);
        String name = (String) tableModel.getValueAt(selectedRow, 1);
        String specialization = (String) tableModel.getValueAt(selectedRow, 2);
        String contact = (String) tableModel.getValueAt(selectedRow, 3);
        String gender = (String) tableModel.getValueAt(selectedRow, 4);
        String experience = (String) tableModel.getValueAt(selectedRow, 5);
        String availability = (String) tableModel.getValueAt(selectedRow, 6);

        JTextField idField = new JTextField(id, 5);
        JTextField nameField = new JTextField(name, 15);
        JTextField specializationField = new JTextField(specialization, 15);
        JTextField contactField = new JTextField(contact, 10);
        JComboBox<String> genderField = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        genderField.setSelectedItem(gender);
        JComboBox<String> experienceField = new JComboBox<>(new String[]{"0-10", "10-20", "20-30", "30+"});
        experienceField.setSelectedItem(experience);
        JTextField availabilityField = new JTextField(availability, 10);

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));

        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Specialization:"));
        panel.add(specializationField);
        panel.add(new JLabel("Contact:"));
        panel.add(contactField);
        panel.add(new JLabel("Gender:"));
        panel.add(genderField);
        panel.add(new JLabel("Experience:"));
        panel.add(experienceField);
        panel.add(new JLabel("Availability:"));
        panel.add(availabilityField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Edit Doctor", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            tableModel.setValueAt(idField.getText(), selectedRow, 0);
            tableModel.setValueAt(nameField.getText(), selectedRow, 1);
            tableModel.setValueAt(specializationField.getText(), selectedRow, 2);
            tableModel.setValueAt(contactField.getText(), selectedRow, 3);
            tableModel.setValueAt(genderField.getSelectedItem(), selectedRow, 4);
            tableModel.setValueAt(experienceField.getSelectedItem(), selectedRow, 5);
            tableModel.setValueAt(availabilityField.getText(), selectedRow, 6);
            saveDataToFile();
        }
    }

    private void deleteDoctor() {
        int selectedRow = doctorTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a doctor to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this doctor?", "Delete Doctor", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            saveDataToFile();
        }
    }

    private void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    Object value = tableModel.getValueAt(i, j);
                    writer.write(value != null ? value.toString() : "");
                    if (j < tableModel.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                tableModel.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DoctorManagement::new);
    }
}