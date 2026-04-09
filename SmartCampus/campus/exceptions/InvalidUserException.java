package campus.exceptions;

/** Part 3: Thrown when a user provides an invalid ID or roll number. */
public class InvalidUserException extends Exception {
    public InvalidUserException(String message) {
        super(message);
    }
}
