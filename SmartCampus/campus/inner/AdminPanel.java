package campus.inner;

import campus.util.StringUtil;

/**
 * Part 4: AdminPanel contains a STATIC nested class Report.
 *
 * WHY static nested: Report is logically part of AdminPanel but doesn't need
 * access to instance state of AdminPanel. It can be instantiated independently
 * (AdminPanel.Report) and is suitable for utility-style report generation.
 */
public class AdminPanel {

    private String adminId;
    private String adminName;

    public AdminPanel(String adminId, String adminName) {
        this.adminId   = adminId;
        this.adminName = adminName;
    }

    public String getAdminId()   { return adminId; }
    public String getAdminName() { return adminName; }

    /**
     * Part 4: Static nested class.
     * Generates service usage reports. Does NOT need AdminPanel instance.
     */
    public static class Report {

        private String reportTitle;
        private int    totalBookings;
        private int    totalComplaints;

        public Report(String reportTitle) {
            this.reportTitle = reportTitle;
        }

        public void addBookings(int n)    { totalBookings   += n; }
        public void addComplaints(int n)  { totalComplaints += n; }

        public void printReport() {
            System.out.println("\n======= " + reportTitle + " =======");
            System.out.println("  Total Bookings  : " + totalBookings);
            System.out.println("  Total Complaints: " + totalComplaints);
            System.out.println("=========================================");
        }
    }

    /** Generates a temporary pass using a LOCAL inner class. */
    public void generateTemporaryPass(String visitorName) {
        /**
         * Part 4: Local inner class — defined INSIDE a method.
         * WHY local: TemporaryPass is only relevant within this method's
         * scope. It encapsulates pass logic without polluting the outer class.
         */
        class TemporaryPass {
            private String visitor;
            private String passCode;

            TemporaryPass(String visitor) {
                this.visitor  = visitor;
                this.passCode = "TEMP-" + (int)(Math.random() * 9000 + 1000);
            }

            void display() {
                System.out.println("  [TempPass] Visitor: " + visitor
                                   + " | Code: " + passCode);
            }
        }

        TemporaryPass pass = new TemporaryPass(visitorName);
        System.out.println("[AdminPanel] Issuing temporary pass to " + visitorName);
        pass.display();
    }

    /** Uses an ANONYMOUS inner class implementing Runnable. */
    public void scheduleAudit() {
        /**
         * Part 4: Anonymous inner class.
         * WHY anonymous: We need a one-off Runnable for the audit task.
         * It's used only here and doesn't need a named class.
         */
        Runnable auditTask = new Runnable() {
            @Override
            public void run() {
                System.out.println("[AdminPanel] Running scheduled audit... (anonymous Runnable)");
                System.out.println("  Audit complete. No issues found.");
            }
        };

        Thread auditThread = new Thread(auditTask, "AuditThread");
        auditThread.start();
        try { auditThread.join(); } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
