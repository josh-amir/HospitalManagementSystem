import java.util.ArrayList;
import java.util.List;

public class PatientRecord {
    private Patient patient;
    private List<String> medicalHistory;
    private List<Appointment> appointments;

    public PatientRecord(Patient patient) {
        this.patient = patient;
        this.medicalHistory = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }

    public Patient getPatient() {
        return patient;
    }

    public List<String> getMedicalHistory() {
        return medicalHistory;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addMedicalHistory(String record) {
        medicalHistory.add(record);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void displayPatientRecord() {
        System.out.println("Patient Record for: " + patient.getName());
        System.out.println("Medical History:");
        for (String record : medicalHistory) {
            System.out.println("- " + record);
        }
        System.out.println("Appointments:");
        for (Appointment appointment : appointments) {
            appointment.displayAppointmentDetails();
        }
    }
}