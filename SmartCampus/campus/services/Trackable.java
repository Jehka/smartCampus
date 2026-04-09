package campus.services;

/**
 * Part 2: Trackable interface — also nests the Notification interface
 * (interface nesting requirement).
 */
public interface Trackable {
    void trackStatus();

    /**
     * Part 2: Nested interface inside Trackable.
     * Notification — campus services can raise alerts.
     */
    interface Notification {

        void sendAlert(String message);

        /**
         * Part 2: Nested class inside nested interface —
         * a concrete EmailNotification implementation.
         */
        class EmailNotification implements Notification {
            private String recipientEmail;

            public EmailNotification(String recipientEmail) {
                this.recipientEmail = recipientEmail;
            }

            @Override
            public void sendAlert(String message) {
                System.out.println("[EMAIL -> " + recipientEmail + "] " + message);
            }
        }
    }
}
