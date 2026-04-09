package campus.services;

/**
 * Part 1: Base Service class.
 * Part 2: Extended by TransportService, LibraryService, PremiumLabService.
 */
public class Service {

    protected String serviceId;
    protected String serviceName;

    public Service(String serviceId, String serviceName) {
        this.serviceId   = serviceId;
        this.serviceName = serviceName;
    }

    public String getServiceId()   { return serviceId; }
    public String getServiceName() { return serviceName; }

    public void showServiceDetails() {
        System.out.println("  Service ID  : " + serviceId);
        System.out.println("  Service Name: " + serviceName);
    }

    @Override
    public String toString() {
        return "Service{id='" + serviceId + "', name='" + serviceName + "'}";
    }
}

// ─── Part 2: final class — cannot be subclassed ───────────────────────────────
/**
 * UniversityRules is final — policy class that no one should override.
 */
final class UniversityRules {

    public static final int MAX_SHUTTLE_SEATS   = 40;
    public static final int MAX_LIBRARY_SEATS   = 80;
    public static final int MAX_LAB_SYSTEMS     = 20;
    public static final double LAB_FEE_PER_HOUR = 50.0;
    public static final double TRANSPORT_FEE    = 20.0;

    // final method — cannot be overridden
    public final String generateReceipt(String userName, String service, double amount) {
        return "=== RECEIPT ===" +
               "\n  User    : " + userName +
               "\n  Service : " + service +
               "\n  Amount  : Rs " + amount +
               "\n===============";
    }

    private UniversityRules() { /* utility class */ }
}
