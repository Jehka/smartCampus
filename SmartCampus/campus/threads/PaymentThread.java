package campus.threads;

import campus.exceptions.PaymentFailedException;

/**
 * Part 7: PaymentThread — processes fee payments.
 * Demonstrates:
 *   - Exception handling inside run()
 *   - Effect of uncaught exception
 *   - UncaughtExceptionHandler
 */
public class PaymentThread extends Thread {

    private final String studentName;
    private final double paymentAmount;

    public PaymentThread(String studentName, double paymentAmount) {
        super("PaymentThread-" + studentName);
        this.studentName   = studentName;
        this.paymentAmount = paymentAmount;

        // Part 7: UncaughtExceptionHandler — catches exceptions that
        // escape run() so the main program can log them properly.
        this.setUncaughtExceptionHandler((thread, ex) ->
            System.out.println("[UncaughtExceptionHandler] Thread: " + thread.getName()
                    + " threw: " + ex.getMessage())
        );
    }

    @Override
    public void run() {
        System.out.println("[" + getName() + "] Processing payment of Rs "
                + paymentAmount + " for " + studentName);
        try {
            Thread.sleep(200);
            processPayment(paymentAmount);
            System.out.println("[" + getName() + "] Payment SUCCESS for " + studentName);
        } catch (PaymentFailedException e) {
            // Exception caught inside run() — handled gracefully
            System.out.println("[" + getName() + "] Payment FAILED: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[" + getName() + "] Interrupted.");
        }
    }

    /**
     * Throws PaymentFailedException if amount is invalid.
     * Demonstrates: 'throw' and 'throws' keywords.
     */
    private void processPayment(double amount) throws PaymentFailedException {
        if (amount < 0) {
            throw new PaymentFailedException(
                "Negative payment amount: Rs " + amount);
        }
        if (amount == 0) {
            throw new PaymentFailedException(
                "Zero payment is not valid for " + studentName);
        }
        // simulate processing
        System.out.println("[" + getName() + "] Deducting Rs " + amount
                + " from account of " + studentName + "...");
    }

    /**
     * Part 7: Demonstrates an UNCAUGHT exception escaping run().
     * This will be caught by UncaughtExceptionHandler set in the constructor.
     */
    public static PaymentThread createBadThread(String name) {
        PaymentThread t = new PaymentThread(name, -999) {
            @Override
            public void run() {
                System.out.println("[" + getName() + "] Intentionally throwing uncaught exception...");
                throw new RuntimeException("Simulated uncaught payment crash for " + name);
            }
        };
        return t;
    }
}
