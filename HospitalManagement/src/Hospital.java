public class Hospital {
    public void admit(Person person) {
        System.out.println(person.getName() + " has been admitted.");
    }

    public void admit(Person person, String room) {
        System.out.println(person.getName() + " has been admitted to room " + room + ".");
    }
}
