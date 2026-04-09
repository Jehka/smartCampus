package campus.threads;

/**
 * Part 5: FacultyThread — created by IMPLEMENTING Runnable.
 * Demonstrates the second way to create threads.
 */
public class FacultyThread implements Runnable {

    private final ResourcePool pool;
    private final String       facultyName;
    private final int          requestCount;

    public FacultyThread(String facultyName, ResourcePool pool, int requestCount) {
        this.facultyName  = facultyName;
        this.pool         = pool;
        this.requestCount = requestCount;
    }

    @Override
    public void run() {
        String tName = Thread.currentThread().getName();
        System.out.println("[" + tName + "] Started. Faculty: " + facultyName);
        try {
            Thread.sleep(150);
            pool.requestResource(facultyName, requestCount);
            Thread.sleep(400);
            pool.releaseResource(facultyName, requestCount);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[" + tName + "] Interrupted.");
        }
        System.out.println("[" + tName + "] Finished.");
    }
}
