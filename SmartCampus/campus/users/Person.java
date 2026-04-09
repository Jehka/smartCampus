package campus.users;

/**
 * Part 1: Base class for all people in the campus system.
 * Demonstrates: private fields, this keyword, method overloading,
 * constructors (default + parameterized), toString(), equals()
 */
public class Person {

    // private fields — accessed only through getters/setters
    private String id;
    private String name;
    private String email;

    // Default constructor
    public Person() {
        this.id    = "UNKNOWN";
        this.name  = "UNKNOWN";
        this.email = "UNKNOWN";
    }

    // Parameterized constructor — uses 'this' to distinguish field vs parameter
    public Person(String id, String name, String email) {
        this.id    = id;
        this.name  = name;
        this.email = email;
    }

    // Getters
    public String getId()    { return id; }
    public String getName()  { return name; }
    public String getEmail() { return email; }

    // Setter uses 'this' keyword
    public void updateEmail(String email) {
        this.email = email;
        System.out.println("[Person] Email updated for " + this.name + " -> " + this.email);
    }

    public void displayInfo() {
        System.out.println("  ID    : " + id);
        System.out.println("  Name  : " + name);
        System.out.println("  Email : " + email);
    }

    // Overriding Object.toString()
    @Override
    public String toString() {
        return "Person{id='" + id + "', name='" + name + "', email='" + email + "'}";
    }

    // Overriding Object.equals()
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person other = (Person) obj;
        return this.id.equals(other.id);
    }
}
