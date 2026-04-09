package campus.users;

/**
 * Part 1: Faculty extends Person.
 * Demonstrates: additional fields, super() constructor chaining
 */
public class Faculty extends Person {

    private String employeeId;
    private String department;

    public Faculty(String id, String name, String email,
                   String employeeId, String department) {
        super(id, name, email);
        this.employeeId = employeeId;
        this.department = department;
    }

    public String getEmployeeId() { return employeeId; }
    public String getDepartment() { return department; }

    @Override
    public void displayInfo() {
        System.out.println("-- Faculty Info --");
        super.displayInfo();
        System.out.println("  Employee ID: " + employeeId);
        System.out.println("  Department : " + department);
    }

    // Overloaded requestService methods (same contract as Student)
    public void requestService() {
        System.out.println("[Faculty " + getName() + "] Requesting default service...");
    }

    public void requestService(String serviceName) {
        System.out.println("[Faculty " + getName() + "] Requesting: " + serviceName);
    }

    public void requestService(String serviceName, int priority) {
        System.out.println("[Faculty " + getName() + "] Requesting: " + serviceName
                           + " | Priority: " + priority);
    }

    @Override
    public String toString() {
        return "Faculty{empId='" + employeeId + "', dept='" + department
               + "', " + super.toString() + "}";
    }
}
