import java.util.Date;

public class StaffShift {
    private Person staffMember;
    private Date shiftStart;
    private Date shiftEnd;

    public StaffShift(Person staffMember, Date shiftStart, Date shiftEnd) {
        this.staffMember = staffMember;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
    }

    public Person getStaffMember() {
        return staffMember;
    }

    public Date getShiftStart() {
        return shiftStart;
    }

    public Date getShiftEnd() {
        return shiftEnd;
    }

    public void displayShiftDetails() {
        System.out.println("Staff Member: " + staffMember.getName());
        System.out.println("Shift Start: " + shiftStart);
        System.out.println("Shift End: " + shiftEnd);
    }
}