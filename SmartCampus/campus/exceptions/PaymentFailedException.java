package campus.exceptions;

/** Part 3: Thrown when a payment amount is invalid or negative. */
public class PaymentFailedException extends Exception {
    public PaymentFailedException(String message) {
        super(message);
    }
}
