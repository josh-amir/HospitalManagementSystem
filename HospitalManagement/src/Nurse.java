public class Nurse extends Person {
    private String department;

    public Nurse(String name, int age, String department) {
        super(name, age);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public void displayInfo() {
        System.out.println("Nurse Name: " + getName() + ", Age: " + getAge() + ", Department: " + department);
    }
}
