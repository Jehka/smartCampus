package campus.services;

import campus.exceptions.SeatNotAvailableException;
import campus.exceptions.PaymentFailedException;

/**
 * Part 2: LibraryService extends Service, implements Bookable + Trackable.
 */
public class LibraryService extends Service implements Bookable, Trackable {

    private int availableTerminals;

    public LibraryService(String serviceId, int totalTerminals) {
        super(serviceId, "Campus Library");
        this.availableTerminals = totalTerminals;
    }

    @Override
    public void bookResource() {
        System.out.println("[LibraryService] Booking library terminal...");
        if (availableTerminals > 0) {
            availableTerminals--;
            System.out.println("  Terminal booked. Remaining: " + availableTerminals);
        } else {
            System.out.println("  ERROR: No terminals available.");
        }
    }

    public void bookTerminal(String userName) throws SeatNotAvailableException {
        if (availableTerminals <= 0) {
            throw new SeatNotAvailableException(
                "No library terminals available for " + userName);
        }
        availableTerminals--;
        System.out.println("[LibraryService] Terminal booked for " + userName
                           + ". Remaining: " + availableTerminals);
    }

    @Override
    public void trackStatus() {
        System.out.println("[LibraryService] Available terminals: " + availableTerminals);
    }
}


/**
 * Part 2: PremiumLabService extends Service, implements Bookable + Payable.
 */
class PremiumLabService extends Service implements Bookable, Payable {

    private int    availableSystems;
    private double feePerHour;

    public PremiumLabService(String serviceId, int systems, double feePerHour) {
        super(serviceId, "Premium Computer Lab");
        this.availableSystems = systems;
        this.feePerHour       = feePerHour;
    }

    @Override
    public void bookResource() {
        System.out.println("[PremiumLabService] Booking lab system...");
        if (availableSystems > 0) {
            availableSystems--;
            System.out.println("  System booked. Remaining: " + availableSystems);
        } else {
            System.out.println("  ERROR: No lab systems available.");
        }
    }

    public void bookSystem(String userName, double paymentAmount)
            throws SeatNotAvailableException, PaymentFailedException {
        if (availableSystems <= 0) {
            throw new SeatNotAvailableException("No lab systems available for " + userName);
        }
        if (paymentAmount < 0) {
            throw new PaymentFailedException("Invalid payment: " + paymentAmount);
        }
        availableSystems--;
        System.out.println("[PremiumLabService] System booked for " + userName
                           + ". Fee paid: Rs " + paymentAmount);
    }

    @Override
    public double calculateFee() {
        return feePerHour;
    }

    @Override
    public void showServiceDetails() {
        super.showServiceDetails();
        System.out.println("  Available Systems: " + availableSystems);
        System.out.println("  Fee/Hour         : Rs " + feePerHour);
    }
}
