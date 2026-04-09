package campus.users;

import campus.exceptions.InvalidUserException;

/**
 * Part 1: Student extends Person.
 * Part 3: Throws InvalidUserException for bad roll numbers.
 * Demonstrates: inheritance, super(), additional fields, method overloading
 */
public class Student extends Person implements Cloneable {

    private String rollNumber;
    private int    semester;

    public Student() {
        super();
        this.rollNumber = "ROLL-000";
        this.semester   = 1;
    }

    public Student(String id, String name, String email,
                   String rollNumber, int semester) throws InvalidUserException {
        super(id, name, email);                   // constructor chaining
        if (rollNumber == null || !rollNumber.matches("ROLL-\\d{3}")) {
            throw new InvalidUserException(
                "Invalid roll number format: " + rollNumber +
                ". Expected pattern: ROLL-XXX");
        }
        this.rollNumber = rollNumber;
        this.semester   = semester;
    }

    public String getRollNumber() { return rollNumber; }
    public int    getSemester()   { return semester; }

    @Override
    public void displayInfo() {
        System.out.println("-- Student Info --");
        super.displayInfo();                      // call parent method
        System.out.println("  Roll No : " + rollNumber);
        System.out.println("  Semester: " + semester);
    }

    // ---- Method Overloading (Part 1 requirement) ----
    public void requestService() {
        System.out.println("[" + getName() + "] Requesting default service...");
    }

    public void requestService(String serviceName) {
        System.out.println("[" + getName() + "] Requesting service: " + serviceName);
    }

    public void requestService(String serviceName, int priority) {
        System.out.println("[" + getName() + "] Requesting service: " + serviceName
                           + " | Priority: " + priority);
    }

    // Part 2: Cloneable implementation
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Student{roll='" + rollNumber + "', semester=" + semester
               + ", " + super.toString() + "}";
    }
}
