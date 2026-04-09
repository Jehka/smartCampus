package campus.threads;

/**
 * Part 5: StudentThread — created by EXTENDING Thread.
 * Requests resources from a shared ResourcePool.
 */
public class StudentThread extends Thread {

    private final ResourcePool pool;
    private final String       studentName;
    private final int          requestCount;

    public StudentThread(String studentName, ResourcePool pool, int requestCount) {
        super("StudentThread-" + studentName);   // setName via super
        this.studentName  = studentName;
        this.pool         = pool;
        this.requestCount = requestCount;
    }

    @Override
    public void run() {
        System.out.println("[" + getName() + "] Started. "
                + "Student: " + studentName);
        try {
            Thread.sleep(100);   // simulate arrival delay
            pool.requestResource(studentName, requestCount);
            Thread.sleep(300);   // simulate using resource
            pool.releaseResource(studentName, requestCount);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[" + getName() + "] Interrupted.");
        }
        System.out.println("[" + getName() + "] Finished.");
    }
}
