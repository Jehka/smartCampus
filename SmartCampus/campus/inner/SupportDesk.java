package campus.inner;

/**
 * Part 4: SupportDesk contains a NON-STATIC inner class Ticket.
 * WHY non-static inner: Each Ticket belongs to a specific desk instance.
 */
public class SupportDesk {
    private String deskName;
    private int    ticketCounter = 0;

    public SupportDesk(String deskName) { this.deskName = deskName; }

    public class Ticket {
        private int    ticketId;
        private String issuerName;
        private String description;
        private String status;

        public Ticket(String issuerName, String description) {
            ticketCounter++;
            this.ticketId    = ticketCounter;
            this.issuerName  = issuerName;
            this.description = description;
            this.status      = "OPEN";
        }
        public void resolve() { this.status = "RESOLVED"; }
        public void display() {
            System.out.println("  [Ticket #" + ticketId + "] Desk: " + deskName
                               + " | By: " + issuerName + " | Status: " + status);
            System.out.println("    Desc: " + description);
        }
    }

    public Ticket createTicket(String issuerName, String description) {
        return new Ticket(issuerName, description);
    }
    public String getDeskName() { return deskName; }
}
