import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class AppointmentManagement extends JFrame {
    private DefaultTableModel tableModel;
    private JTable appointmentTable;
    private static final String FILE_NAME = "appointments_data.txt";

    public AppointmentManagement() {
        setTitle("Appointment Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize table model and table
        String[] columnNames = {"Patient Name", "Doctor Name", "Date", "Time", "Reason"};
        tableModel = new DefaultTableModel(columnNames, 0);
        appointmentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load data from file
        loadDataFromFile();

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add Appointment");
        JButton editButton = new JButton("Edit Appointment");
        JButton deleteButton = new JButton("Delete Appointment");
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
                showAppointmentForm(null);
            }
        });

        // Edit button action
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = appointmentTable.getSelectedRow();
                if (selectedRow != -1) {
                    String patientName = (String) tableModel.getValueAt(selectedRow, 0);
                    String doctorName = (String) tableModel.getValueAt(selectedRow, 1);
                    String date = (String) tableModel.getValueAt(selectedRow, 2);
                    String time = (String) tableModel.getValueAt(selectedRow, 3);
                    String reason = (String) tableModel.getValueAt(selectedRow, 4);
                    Appointment appointment = new Appointment(patientName, doctorName, date, time, reason);
                    showAppointmentForm(appointment);
                }
            }
        });

        // Delete button action
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = appointmentTable.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                    saveDataToFile();
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

    private void showAppointmentForm(Appointment appointment) {
        JDialog dialog = new JDialog(this, "Appointment Form", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(6, 2, 10, 10));
        dialog.getContentPane().setBackground(Color.WHITE);

        dialog.add(new JLabel("Patient Name:"));
        JTextField patientNameField = new JTextField(appointment != null ? appointment.getPatientName() : "");
        dialog.add(patientNameField);

        dialog.add(new JLabel("Doctor Name:"));
        JTextField doctorNameField = new JTextField(appointment != null ? appointment.getDoctorName() : "");
        dialog.add(doctorNameField);

        dialog.add(new JLabel("Date:"));
        JTextField dateField = new JTextField(appointment != null ? appointment.getDate() : "");
        dialog.add(dateField);

        dialog.add(new JLabel("Time:"));
        JTextField timeField = new JTextField(appointment != null ? appointment.getTime() : "");
        dialog.add(timeField);

        dialog.add(new JLabel("Reason:"));
        JTextField reasonField = new JTextField(appointment != null ? appointment.getReason() : "");
        dialog.add(reasonField);

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(34, 139, 34)); // Forest green
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (appointment == null) {
                    Appointment newAppointment = new Appointment(
                            patientNameField.getText(),
                            doctorNameField.getText(),
                            dateField.getText(),
                            timeField.getText(),
                            reasonField.getText()
                    );
                    tableModel.addRow(new Object[]{
                            newAppointment.getPatientName(),
                            newAppointment.getDoctorName(),
                            newAppointment.getDate(),
                            newAppointment.getTime(),
                            newAppointment.getReason()
                    });
                } else {
                    int selectedRow = appointmentTable.getSelectedRow();
                    tableModel.setValueAt(patientNameField.getText(), selectedRow, 0);
                    tableModel.setValueAt(doctorNameField.getText(), selectedRow, 1);
                    tableModel.setValueAt(dateField.getText(), selectedRow, 2);
                    tableModel.setValueAt(timeField.getText(), selectedRow, 3);
                    tableModel.setValueAt(reasonField.getText(), selectedRow, 4);
                }
                saveDataToFile();
                dialog.dispose();
            }
        });
        dialog.add(saveButton);

        dialog.setVisible(true);
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

    public static void main(String[] args) {
        new AppointmentManagement();
    }
}

class Appointment {
    private String patientName;
    private String doctorName;
    private String date;
    private String time;
    private String reason;

    public Appointment(String patientName, String doctorName, String date, String time, String reason) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.date = date;
        this.time = time;
        this.reason = reason;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getReason() {
        return reason;
    }

    public void displayAppointmentDetails() {
        System.out.println("Appointment Details:");
        System.out.println("Patient Name: " + patientName);
        System.out.println("Doctor Name: " + doctorName);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Reason: " + reason);
    }
}