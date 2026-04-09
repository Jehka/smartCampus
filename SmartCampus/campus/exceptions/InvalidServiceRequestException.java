package campus.exceptions;

/** Part 3: Thrown when a requested service name does not exist. */
public class InvalidServiceRequestException extends Exception {
    public InvalidServiceRequestException(String message) {
        super(message);
    }
}
