import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class PatientManagement extends JFrame {
    private DefaultListModel<Patient> patientListModel;
    private JList<Patient> patientList;
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public PatientManagement() {
        setTitle("Patient Management");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize patientListModel and patientList
        patientListModel = new DefaultListModel<>();
        patientList = new JList<>(patientListModel);

        // Initialize tableModel and patientTable
        String[] columnNames = {"Name", "Age", "Gender", "Contact", "Nationality", "Medical History", "Admitted Disease", "Assigned Doctor", "Emergency Contact"};
        tableModel = new DefaultTableModel(columnNames, 0);
        patientTable = new JTable(tableModel);
        patientTable.setFont(new Font("Arial", Font.PLAIN, 14));
        patientTable.setRowHeight(30);
        patientTable.setGridColor(Color.LIGHT_GRAY);

        // Load patient data from file
        loadPatientDataFromFile();

        // Logo and Search bar panel
        JPanel northPanel = new JPanel(new BorderLayout());
        ImageIcon logoIcon = new ImageIcon("ngojomedicalhospitalfinal.png");
        if (logoIcon.getIconWidth() == -1) {
            System.err.println("Logo image not found.");
        } else {
            Image image = logoIcon.getImage(); // transform it
            Image newimg = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            logoIcon = new ImageIcon(newimg);  // transform it back
        }
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        northPanel.add(logoLabel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterPatients(searchField.getText().toLowerCase());
            }
        });
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        northPanel.add(searchPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);

        // Table header customization
        JTableHeader header = patientTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(173, 216, 230)); // Light blue
        header.setForeground(Color.BLACK);

        // Table cell customization
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        cellRenderer.setBackground(new Color(240, 255, 255)); // Light cyan
        patientTable.setDefaultRenderer(Object.class, cellRenderer);

        add(new JScrollPane(patientTable), BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add Patient");
        JButton editButton = new JButton("Edit Patient");
        JButton deleteButton = new JButton("Delete Patient");
        JButton backButton = new JButton("Back");

        // Set button colors
        addButton.setBackground(new Color(34, 139, 34)); // Forest green
        addButton.setForeground(Color.WHITE);
        editButton.setBackground(new Color(255, 165, 0)); // Orange
        editButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(220, 20, 60)); // Crimson
        deleteButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(70, 130, 180)); // Steel blue
        backButton.setForeground(Color.WHITE);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add button action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPatientForm(null);
            }
        });

        // Edit button action
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = patientTable.getSelectedRow();
                if (selectedRow != -1) {
                    Patient patient = new Patient(
                            (String) tableModel.getValueAt(selectedRow, 0),
                            Integer.parseInt((String) tableModel.getValueAt(selectedRow, 1)),
                            (String) tableModel.getValueAt(selectedRow, 2),
                            (String) tableModel.getValueAt(selectedRow, 3),
                            (String) tableModel.getValueAt(selectedRow, 4),
                            (String) tableModel.getValueAt(selectedRow, 5),
                            (String) tableModel.getValueAt(selectedRow, 6),
                            (String) tableModel.getValueAt(selectedRow, 7),
                            (String) tableModel.getValueAt(selectedRow, 8)
                    );
                    showPatientForm(patient);
                }
            }
        });

        // Delete button action
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = patientTable.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                    savePatientDataToFile();
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

        setVisible(true);
    }

    private void filterPatients(String query) {
        DefaultTableModel filteredModel = new DefaultTableModel(new String[]{"Name", "Age", "Gender", "Contact", "Nationality", "Medical History", "Admitted Disease", "Assigned Doctor", "Emergency Contact"}, 0);
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String name = tableModel.getValueAt(i, 0).toString().toLowerCase();
            String age = tableModel.getValueAt(i, 1).toString().toLowerCase();
            String gender = tableModel.getValueAt(i, 2).toString().toLowerCase();
            String contact = tableModel.getValueAt(i, 3).toString().toLowerCase();
            String nationality = tableModel.getValueAt(i, 4).toString().toLowerCase();
            String medicalHistory = tableModel.getValueAt(i, 5).toString().toLowerCase();
            String admittedDisease = tableModel.getValueAt(i, 6).toString().toLowerCase();
            String assignedDoctor = tableModel.getValueAt(i, 7).toString().toLowerCase();
            String emergencyContact = tableModel.getValueAt(i, 8).toString().toLowerCase();

            if (name.contains(query) || age.contains(query) || gender.contains(query) || contact.contains(query) || nationality.contains(query) || medicalHistory.contains(query) || admittedDisease.contains(query) || assignedDoctor.contains(query) || emergencyContact.contains(query)) {
                filteredModel.addRow(new Object[]{name, age, gender, contact, nationality, medicalHistory, admittedDisease, assignedDoctor, emergencyContact});
            }
        }
        patientTable.setModel(filteredModel);
    }

    private void showPatientForm(Patient patient) {
        JDialog dialog = new JDialog(this, "Patient Form", true);
        dialog.setSize(400, 600);
        dialog.setLayout(new GridLayout(11, 2, 10, 10));
        dialog.getContentPane().setBackground(Color.WHITE);
        dialog.getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));

        dialog.add(new JLabel("Name:"));
        JTextField nameField = new JTextField(patient != null ? patient.getName() : "");
        dialog.add(nameField);

        dialog.add(new JLabel("Age:"));
        JTextField ageField = new JTextField(patient != null ? String.valueOf(patient.getAge()) : "");
        dialog.add(ageField);

        dialog.add(new JLabel("Gender:"));
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        if (patient != null) {
            genderComboBox.setSelectedItem(patient.getGender());
        }
        dialog.add(genderComboBox);

        dialog.add(new JLabel("Contact:"));
        JTextField contactField = new JTextField(patient != null ? patient.getContact() : "");
        dialog.add(contactField);

        dialog.add(new JLabel("Nationality:"));
        JComboBox<String> nationalityComboBox = new JComboBox<>(new String[]{
                "Afghan", "Albanian", "Algerian", "American", "Andorran", "Angolan", "Antiguan", "Argentine", "Armenian", "Australian", "Austrian", "Azerbaijani",
                "Bahamian", "Bahraini", "Bangladeshi", "Barbadian", "Barbudan", "Batswana", "Belarusian", "Belgian", "Belizean", "Beninese", "Bhutanese", "Bolivian",
                "Bosnian", "Brazilian", "British", "Bruneian", "Bulgarian", "Burkinabe", "Burmese", "Burundian", "Cabo Verdean", "Cambodian", "Cameroonian", "Canadian",
                "Central African", "Chadian", "Chilean", "Chinese", "Colombian", "Comoran", "Congolese", "Costa Rican", "Croatian", "Cuban", "Cypriot", "Czech",
                "Danish", "Djiboutian", "Dominican", "Dutch", "East Timorese", "Ecuadorean", "Egyptian", "Emirati", "Equatorial Guinean", "Eritrean", "Estonian",
                "Ethiopian", "Fijian", "Filipino", "Finnish", "French", "Gabonese", "Gambian", "Georgian", "German", "Ghanaian", "Greek", "Grenadian", "Guatemalan",
                "Guinea-Bissauan", "Guinean", "Guyanese", "Haitian", "Herzegovinian", "Honduran", "Hungarian", "I-Kiribati", "Icelander", "Indian", "Indonesian",
                "Iranian", "Iraqi", "Irish", "Israeli", "Italian", "Ivorian", "Jamaican", "Japanese", "Jordanian", "Kazakhstani", "Kenyan", "Kittian and Nevisian",
                "Kuwaiti", "Kyrgyz", "Laotian", "Latvian", "Lebanese", "Liberian", "Libyan", "Liechtensteiner", "Lithuanian", "Luxembourger", "Macedonian", "Malagasy",
                "Malawian", "Malaysian", "Maldivian", "Malian", "Maltese", "Marshallese", "Mauritanian", "Mauritian", "Mexican", "Micronesian", "Moldovan", "Monacan",
                "Mongolian", "Moroccan", "Mosotho", "Motswana", "Mozambican", "Namibian", "Nauruan", "Nepalese", "New Zealander", "Nicaraguan", "Nigerian", "Nigerien",
                "North Korean", "Northern Irish", "Norwegian", "Omani", "Pakistani", "Palauan", "Palestinian", "Panamanian", "Papua New Guinean", "Paraguayan", "Peruvian",
                "Polish", "Portuguese", "Qatari", "Romanian", "Russian", "Rwandan", "Saint Lucian", "Salvadoran", "Samoan", "San Marinese", "Sao Tomean", "Saudi",
                "Scottish", "Senegalese", "Serbian", "Seychellois", "Sierra Leonean", "Singaporean", "Slovakian", "Slovenian", "Solomon Islander", "Somali", "South African",
                "South Korean", "South Sudanese", "Spanish", "Sri Lankan", "Sudanese", "Surinamer", "Swazi", "Swedish", "Swiss", "Syrian", "Taiwanese", "Tajik", "Tanzanian",
                "Thai", "Togolese", "Tongan", "Trinidadian or Tobagonian", "Tunisian", "Turkish", "Tuvaluan", "Ugandan", "Ukrainian", "Uruguayan", "Uzbekistani", "Vanuatuan",
                "Venezuelan", "Vietnamese", "Welsh", "Yemenite", "Zambian", "Zimbabwean"
        });
        if (patient != null) {
            nationalityComboBox.setSelectedItem(patient.getNationality());
        }
        dialog.add(nationalityComboBox);

        dialog.add(new JLabel("Medical History:"));
        JTextField medicalHistoryField = new JTextField(patient != null ? patient.getMedicalHistory() : "");
        dialog.add(medicalHistoryField);

        dialog.add(new JLabel("Admitted Disease:"));
        JTextField admittedDiseaseField = new JTextField(patient != null ? patient.getAdmittedDisease() : "");
        dialog.add(admittedDiseaseField);

        dialog.add(new JLabel("Assigned Doctor:"));
        JTextField assignedDoctorField = new JTextField(patient != null ? patient.getAssignedDoctor() : "");
        dialog.add(assignedDoctorField);

        dialog.add(new JLabel("Emergency Contact:"));
        JTextField emergencyContactField = new JTextField(patient != null ? patient.getEmergencyContact() : "");
        dialog.add(emergencyContactField);

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(34, 139, 34)); // Forest green
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (patient == null) {
                    Patient newPatient = new Patient(
                            nameField.getText(),
                            Integer.parseInt(ageField.getText()),
                            (String) genderComboBox.getSelectedItem(),
                            contactField.getText(),
                            (String) nationalityComboBox.getSelectedItem(),
                            medicalHistoryField.getText(),
                            admittedDiseaseField.getText(),
                            assignedDoctorField.getText(),
                            emergencyContactField.getText()
                    );
                    patientListModel.addElement(newPatient);
                    tableModel.addRow(new Object[]{
                            newPatient.getName(),
                            newPatient.getAge(),
                            newPatient.getGender(),
                            newPatient.getContact(),
                            newPatient.getNationality(),
                            newPatient.getMedicalHistory(),
                            newPatient.getAdmittedDisease(),
                            newPatient.getAssignedDoctor(),
                            newPatient.getEmergencyContact()
                    });
                } else {
                    patient.setName(nameField.getText());
                    patient.setAge(Integer.parseInt(ageField.getText()));
                    patient.setGender((String) genderComboBox.getSelectedItem());
                    patient.setContact(contactField.getText());
                    patient.setNationality((String) nationalityComboBox.getSelectedItem());
                    patient.setMedicalHistory(medicalHistoryField.getText());
                    patient.setAdmittedDisease(admittedDiseaseField.getText());
                    patient.setAssignedDoctor(assignedDoctorField.getText());
                    patient.setEmergencyContact(emergencyContactField.getText());

                    int selectedIndex = patientList.getSelectedIndex();
                    patientListModel.setElementAt(patient, selectedIndex);

                    int selectedRow = patientTable.getSelectedRow();
                    tableModel.setValueAt(patient.getName(), selectedRow, 0);
                    tableModel.setValueAt(patient.getAge(), selectedRow, 1);
                    tableModel.setValueAt(patient.getGender(), selectedRow, 2);
                    tableModel.setValueAt(patient.getContact(), selectedRow, 3);
                    tableModel.setValueAt(patient.getNationality(), selectedRow, 4);
                    tableModel.setValueAt(patient.getMedicalHistory(), selectedRow, 5);
                    tableModel.setValueAt(patient.getAdmittedDisease(), selectedRow, 6);
                    tableModel.setValueAt(patient.getAssignedDoctor(), selectedRow, 7);
                    tableModel.setValueAt(patient.getEmergencyContact(), selectedRow, 8);
                }
                savePatientDataToFile();
                dialog.dispose();
            }
        });
        dialog.add(saveButton);

        dialog.setVisible(true);
    }

    private void loadPatientDataFromFile() {
        try (Scanner scanner = new Scanner(new File("patient_data.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                Patient patient = new Patient(data[0], Integer.parseInt(data[1]), data[2], data[3], data[4], data[5], data[6], data[7], data[8]);
                patientListModel.addElement(patient);
                tableModel.addRow(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void savePatientDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("patient_data.txt"))) {
            for (int i = 0; i < patientListModel.size(); i++) {
                Patient patient = patientListModel.getElementAt(i);
                writer.write(patient.getName() + "," + patient.getAge() + "," + patient.getGender() + "," + patient.getContact() + "," + patient.getNationality() + "," + patient.getMedicalHistory() + "," + patient.getAdmittedDisease() + "," + patient.getAssignedDoctor() + "," + patient.getEmergencyContact());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PatientManagement();
    }
}