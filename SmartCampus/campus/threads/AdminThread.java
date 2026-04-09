package campus.threads;

/**
 * Part 5: AdminThread adds resources after a delay (notifyAll wakes waiters).
 * Part 6: Demonstrates setName() via super() and why Thread.stop() is unsafe.
 *
 * WHY NOT Thread.stop():
 *   Thread.stop() forcibly releases ALL monitors the thread holds, leaving
 *   shared objects in inconsistent/corrupt state. A volatile boolean flag
 *   lets the thread finish its current work unit cleanly before stopping.
 */
public class AdminThread extends Thread {
    private final ResourcePool pool;
    private final int          resourcesToAdd;
    private final int          delayMs;

    public AdminThread(ResourcePool pool, int resourcesToAdd, int delayMs) {
        super("AdminThread");
        this.pool           = pool;
        this.resourcesToAdd = resourcesToAdd;
        this.delayMs        = delayMs;
    }

    @Override
    public void run() {
        System.out.println("[" + getName() + "] Admin thread started. Will add "
                + resourcesToAdd + " resources after " + delayMs + "ms.");
        try {
            Thread.sleep(delayMs);
            pool.addResources(resourcesToAdd);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[" + getName() + "] Interrupted before adding resources.");
        }
        System.out.println("[" + getName() + "] Done.");
    }
}
