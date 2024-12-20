public class Patient {
    private String name;
    private int age;
    private String gender;
    private String contact;
    private String nationality;
    private String medicalHistory;
    private String admittedDisease;
    private String assignedDoctor;
    private String emergencyContact;
    private String dateAdmitted;
    private String dateDischarged;

    public Patient(String name, int age, String gender, String contact, String nationality, String medicalHistory, String admittedDisease, String assignedDoctor, String emergencyContact) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.nationality = nationality;
        this.medicalHistory = medicalHistory;
        this.admittedDisease = admittedDisease;
        this.assignedDoctor = assignedDoctor;
        this.emergencyContact = emergencyContact;
    }

    // Getters and setters for all fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getAdmittedDisease() {
        return admittedDisease;
    }

    public void setAdmittedDisease(String admittedDisease) {
        this.admittedDisease = admittedDisease;
    }

    public String getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(String assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getDateAdmitted() {
        return dateAdmitted;
    }

    public void setDateAdmitted(String dateAdmitted) {
        this.dateAdmitted = dateAdmitted;
    }

    public String getDateDischarged() {
        return dateDischarged;
    }

    public void setDateDischarged(String dateDischarged) {
        this.dateDischarged = dateDischarged;
    }
}