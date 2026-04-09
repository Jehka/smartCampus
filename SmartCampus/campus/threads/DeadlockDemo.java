package campus.threads;

/**
 * Final Scenario: Deadlock demonstration + corrected version.
 *
 * DEADLOCK CAUSE:
 *   Thread A locks resourceA then waits for resourceB.
 *   Thread B locks resourceB then waits for resourceA.
 *   Both wait forever — circular dependency.
 *
 * FIX:
 *   Always acquire locks in the SAME ORDER across all threads.
 *   Both threads lock resourceA first, then resourceB.
 */
public class DeadlockDemo {

    // Locks for the deadlock demo
    private static final Object resourceA = new Object();
    private static final Object resourceB = new Object();

    // Separate fresh locks for the corrected demo.
    // We do NOT reuse resourceA/resourceB because interrupting a thread that
    // is blocked inside synchronized() does NOT release the monitor — the
    // lock stays held until the thread actually exits the block. Reusing the
    // same objects risks the corrected threads inheriting a held monitor.
    private static final Object fixedA = new Object();
    private static final Object fixedB = new Object();

    /** Run this and it will hang — deadlock. (Timeout after 3s in MainApp.) */
    public static void runDeadlockExample() throws InterruptedException {
        System.out.println("\n[DeadlockDemo] === DEADLOCK EXAMPLE (will timeout in 3s) ===");

        Thread t1 = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println("  [T1] Locked resourceA, waiting for resourceB...");
                try { Thread.sleep(100); } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); return;
                }
                synchronized (resourceB) {
                    System.out.println("  [T1] Locked resourceB — DONE");
                }
            }
        }, "DeadlockThread-1");

        Thread t2 = new Thread(() -> {
            synchronized (resourceB) {          // opposite lock order -> deadlock
                System.out.println("  [T2] Locked resourceB, waiting for resourceA...");
                try { Thread.sleep(100); } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); return;
                }
                synchronized (resourceA) {
                    System.out.println("  [T2] Locked resourceA — DONE");
                }
            }
        }, "DeadlockThread-2");

        t1.start();
        t2.start();

        // Give them 3 seconds then interrupt to break the demo
        t1.join(3000);
        t2.join(3000);

        if (t1.isAlive() || t2.isAlive()) {
            System.out.println("  [DeadlockDemo] Deadlock detected! Interrupting threads.");
            t1.interrupt();
            t2.interrupt();
        }
    }

    /** Corrected version — consistent lock ordering eliminates deadlock. */
    public static void runCorrectedExample() throws InterruptedException {
        System.out.println("\n[DeadlockDemo] === CORRECTED EXAMPLE (consistent lock order) ===");

        Thread t1 = new Thread(() -> {
            synchronized (fixedA) {             // fresh locks, always A then B
                System.out.println("  [T1-fixed] Locked fixedA...");
                try { Thread.sleep(100); } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); return;
                }
                synchronized (fixedB) {
                    System.out.println("  [T1-fixed] Locked fixedB - DONE");
                }
            }
        }, "FixedThread-1");

        Thread t2 = new Thread(() -> {
            synchronized (fixedA) {             // same order — no circular wait possible
                System.out.println("  [T2-fixed] Locked fixedA...");
                try { Thread.sleep(100); } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); return;
                }
                synchronized (fixedB) {
                    System.out.println("  [T2-fixed] Locked fixedB - DONE");
                }
            }
        }, "FixedThread-2");

        t1.start();
        t2.start();
        t1.join(3000);
        t2.join(3000);
        System.out.println("  [DeadlockDemo] Both threads completed - no deadlock!");
    }
}
