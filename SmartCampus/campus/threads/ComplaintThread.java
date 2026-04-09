package campus.threads;

public class ComplaintThread extends Thread {
    private volatile boolean running = true;
    private final String userId;
    private final int    intervalMs;

    public ComplaintThread(String userId, int intervalMs) {
        super("ComplaintThread-" + userId);
        this.userId     = userId;
        this.intervalMs = intervalMs;
    }

    public void stopGracefully() {
        running = false;
        System.out.println("[" + getName() + "] Stop signal received.");
    }

    @Override
    public void run() {
        System.out.println("[" + getName() + "] Complaint thread started for " + userId);
        int count = 0;
        while (running) {
            count++;
            System.out.println("[" + getName() + "] Complaint #" + count + " logged by " + userId);
            try { Thread.sleep(intervalMs); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); break; }
        }
        System.out.println("[" + getName() + "] Stopped after " + count + " complaint(s).");
    }
}
