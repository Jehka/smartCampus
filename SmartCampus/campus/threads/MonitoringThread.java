package campus.threads;

public class MonitoringThread extends Thread {
    private volatile boolean running = true;
    private final ResourcePool pool;
    private final int          intervalMs;

    public MonitoringThread(ResourcePool pool, int intervalMs, ThreadGroup group) {
        super(group, "MonitoringThread");
        this.pool       = pool;
        this.intervalMs = intervalMs;
    }

    public void stopGracefully() { running = false; }

    @Override
    public void run() {
        System.out.println("[" + getName() + "] Monitoring started. Group: " + getThreadGroup().getName());
        while (running) {
            Thread me = Thread.currentThread();
            System.out.println("[" + me.getName() + "] MONITOR | Pool: " + pool.getPoolName()
                    + " | Available: " + pool.getAvailable() + " | Booked: " + pool.getBooked());
            try { Thread.sleep(intervalMs); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); break; }
        }
        System.out.println("[" + getName() + "] Monitoring stopped.");
    }
}
