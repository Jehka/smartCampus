package campus.users;

public class FacultyUser extends User implements SecureAccess {
    private String department;
    public FacultyUser(String userId, String name, String department) {
        super(userId, name);
        this.department = department;
    }
    @Override
    public void accessService() {
        System.out.println("[FacultyUser:" + name + "] Accessing campus service (Dept: " + department + ")");
    }
    @Override
    public String toString() { return "FacultyUser{" + super.toString() + ", dept='" + department + "'}"; }
}
