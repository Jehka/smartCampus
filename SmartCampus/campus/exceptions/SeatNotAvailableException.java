package campus.exceptions;

/** Part 3: Thrown when booking exceeds available seats/resources. */
public class SeatNotAvailableException extends Exception {
    public SeatNotAvailableException(String message) {
        super(message);
    }
}
