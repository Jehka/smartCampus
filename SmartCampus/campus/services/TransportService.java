package campus.services;

import campus.exceptions.SeatNotAvailableException;
import campus.exceptions.PaymentFailedException;

/**
 * Part 2: TransportService extends Service, implements Bookable + Payable + Trackable.
 * Demonstrates: super() constructor chaining, method overriding, upcasting.
 */
public class TransportService extends Service implements Bookable, Payable, Trackable {

    private int availableSeats;
    private int bookedSeats;
    private static final double BASE_FEE = 20.0;

    public TransportService(String serviceId, int totalSeats) {
        super(serviceId, "Campus Shuttle Transport");   // super() constructor chaining
        this.availableSeats = totalSeats;
        this.bookedSeats    = 0;
    }

    @Override
    public void bookResource() {
        System.out.println("[TransportService] Booking shuttle seat...");
        try {
            bookSeat("Anonymous", 1);
        } catch (SeatNotAvailableException e) {
            System.out.println("  ERROR: " + e.getMessage());
        }
    }

    public void bookSeat(String userName, int count) throws SeatNotAvailableException {
        if (count > availableSeats) {
            throw new SeatNotAvailableException(
                "Only " + availableSeats + " seats available; requested " + count);
        }
        availableSeats -= count;
        bookedSeats    += count;
        System.out.println("[TransportService] " + count + " seat(s) booked for "
                           + userName + ". Remaining: " + availableSeats);
    }

    @Override
    public double calculateFee() {
        return BASE_FEE * bookedSeats;
    }

    public double calculateFee(double paymentAmount) throws PaymentFailedException {
        if (paymentAmount < 0) {
            throw new PaymentFailedException("Payment amount cannot be negative: " + paymentAmount);
        }
        return paymentAmount;
    }

    @Override
    public void trackStatus() {
        System.out.println("[TransportService] Available: " + availableSeats
                           + " | Booked: " + bookedSeats);
    }

    @Override
    public void showServiceDetails() {
        super.showServiceDetails();
        System.out.println("  Available Seats: " + availableSeats);
        System.out.println("  Booked Seats   : " + bookedSeats);
    }
}
