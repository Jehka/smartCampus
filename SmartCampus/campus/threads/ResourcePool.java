package campus.threads;

/**
 * Parts 5-6: ResourcePool — shared limited resource (e.g. shuttle seats).
 * Demonstrates: synchronized methods, wait(), notify(), notifyAll().
 */
public class ResourcePool {

    private int availableResources;
    private int bookedResources;
    private final String poolName;

    public ResourcePool(String poolName, int initialResources) {
        this.poolName           = poolName;
        this.availableResources = initialResources;
        this.bookedResources    = 0;
    }

    /**
     * Synchronized request — thread waits if resources are insufficient.
     * Uses wait() so the monitor is released while waiting.
     */
    public synchronized void requestResource(String userName, int count) {
        System.out.println("[" + Thread.currentThread().getName() + "] "
                + userName + " requesting " + count + " unit(s) from " + poolName
                + " | Available: " + availableResources);

        while (availableResources < count) {
            System.out.println("[" + Thread.currentThread().getName() + "] "
                    + userName + " WAITING - not enough resources.");
            try {
                wait();     // releases lock, suspends thread
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[" + Thread.currentThread().getName() + "] "
                        + userName + " interrupted while waiting.");
                return;
            }
        }

        availableResources -= count;
        bookedResources    += count;
        System.out.println("[" + Thread.currentThread().getName() + "] "
                + userName + " BOOKED " + count + " unit(s). "
                + "Available now: " + availableResources);
    }

    /**
     * Synchronized release — uses notify() to wake ONE waiting thread.
     */
    public synchronized void releaseResource(String userName, int count) {
        availableResources += count;
        bookedResources    -= count;
        System.out.println("[" + Thread.currentThread().getName() + "] "
                + userName + " RELEASED " + count + " unit(s). "
                + "Available now: " + availableResources);
        notify();   // wake one waiting thread
    }

    /**
     * Synchronized add — uses notifyAll() to wake ALL waiting threads.
     * Called by Admin thread after procuring more resources.
     */
    public synchronized void addResources(int n) {
        availableResources += n;
        System.out.println("[" + Thread.currentThread().getName() + "] "
                + "Admin ADDED " + n + " resource(s) to " + poolName
                + ". Available now: " + availableResources);
        notifyAll();    // wake every waiting thread
    }

    public synchronized int getAvailable() { return availableResources; }
    public synchronized int getBooked()    { return bookedResources;    }
    public String getPoolName()            { return poolName;           }
}
