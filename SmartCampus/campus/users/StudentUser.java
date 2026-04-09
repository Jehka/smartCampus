package campus.users;

public class StudentUser extends User implements SecureAccess {
    private int semester;
    public StudentUser(String userId, String name, int semester) {
        super(userId, name);
        this.semester = semester;
    }
    @Override
    public void accessService() {
        System.out.println("[StudentUser:" + name + "] Accessing campus service (Semester " + semester + ")");
    }
    @Override
    public String toString() { return "StudentUser{" + super.toString() + ", semester=" + semester + "}"; }
}
