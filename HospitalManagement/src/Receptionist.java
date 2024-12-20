public class Receptionist extends Person {
    private String shift;

    public Receptionist(String name, int age, String shift) {
        super(name, age);
        this.shift = shift;
    }

    public String getShift() {
        return shift;
    }

    @Override
    public void displayInfo() {
        System.out.println("Receptionist Name: " + getName() + ", Age: " + getAge() + ", Shift: " + shift);
    }
}
