package campus.main;

import campus.users.*;
import campus.services.*;
import campus.exceptions.*;
import campus.util.StringUtil;
import campus.inner.*;
import campus.threads.*;

/**
 * MainApp — Smart Campus Service Management System
 * Demonstrates ALL parts of the assignment in sequence.
 */
public class MainApp {

    static void banner(String title) {
        System.out.println("\n========== " + title + " ==========");
    }

    static void section(String title) {
        System.out.println("\n--- " + title + " ---");
    }

    // ══════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) throws Exception {

        // ─────────────────────────────────────────────────────────────────────
        // PART 1: Basic Classes
        // ─────────────────────────────────────────────────────────────────────
        banner("PART 1 - Basic Classes");

        // Default and parameterized constructors
        Person p0 = new Person();
        Person p1 = new Person("P001", "Jehkaran", "jeh@upes.ac.in");
        p0.displayInfo();
        p1.displayInfo();
        p1.updateEmail("jeh.new@upes.ac.in");

        // Student with valid roll number
        Student s1 = null, s2 = null;
        try {
            s1 = new Student("S001", "Srishti Soni", "srish@upes.ac.in", "ROLL-101", 3);
            s2 = new Student("S002", "Sneha Jain", "sneh@upes.ac.in", "ROLL-102", 5);
            s1.displayInfo();
        } catch (InvalidUserException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        // Faculty
        Faculty f1 = new Faculty("F001", "Dr. Abhinav Sharma", "abhinavsharma@upes.ac.in", "EMP-501", "ECE");
        f1.displayInfo();

        // Method overloading demonstration
        section("Method Overloading");
        if (s1 != null) {
            s1.requestService();
            s1.requestService("Library");
            s1.requestService("Premium Lab", 2);
        }
        f1.requestService("Transport", 1);

        // Arrays of users and services
        section("Arrays of Users and Services");
        Person[] people = { p1, s1, s2, f1 };
        for (int i = 0; i < people.length; i++) {
            if (people[i] != null)
                System.out.println("  [" + i + "] " + people[i]);
        }

        // Switch menu simulation
        section("Switch Menu - Service Selection");
        int[] choices = {1, 2, 3, 4, 5};
        for (int choice : choices) {
            switch (choice) {
                case 1:  System.out.println("  Selected: Transport Service");  break;
                case 2:  System.out.println("  Selected: Library Service");    break;
                case 3:  System.out.println("  Selected: Lab Service");        break;
                case 4:  System.out.println("  Selected: Complaint Desk");     break;
                default: System.out.println("  Invalid choice: " + choice);
            }
        }

        // if-else, while, break, continue
        section("Control Flow");
        int i = 0;
        while (i < 6) {
            i++;
            if (i == 3) { System.out.println("  Skipping i=3"); continue; }
            if (i == 5) { System.out.println("  Breaking at i=5"); break; }
            System.out.println("  Processing i=" + i);
        }

        // ─────────────────────────────────────────────────────────────────────
        // PART 2: Inheritance, Interfaces, OOP
        // ─────────────────────────────────────────────────────────────────────
        banner("PART 2 - Inheritance, Interfaces, Polymorphism");

        TransportService transport = new TransportService("SVC-T01", 10);
        LibraryService   library   = new LibraryService("SVC-L01", 5);

        section("Service Details");
        transport.showServiceDetails();
        library.showServiceDetails();

        // Upcasting — Service reference holds TransportService
        Service svc = transport;
        System.out.println("\n[Upcasting] " + svc.getServiceName());

        // Downcasting back
        if (svc instanceof TransportService) {
            TransportService ts = (TransportService) svc;
            ts.trackStatus();
        }

        // Bookable upcasting
        Bookable b = transport;
        b.bookResource();

        // toString() and equals()
        section("toString() and equals()");
        System.out.println("s1.toString(): " + s1);
        System.out.println("f1.toString(): " + f1);
        Person dup = new Person("P001", "Duplicate", "dup@x.com");
        System.out.println("p1.equals(dup): " + p1.equals(dup));   // true — same id

        // clone() — Part 2 requirement
        section("clone()");
        try {
            Student cloned = (Student) s1.clone();
            System.out.println("Cloned: " + cloned);
            System.out.println("Same ref? " + (s1 == cloned));
        } catch (CloneNotSupportedException e) {
            System.out.println("Clone failed: " + e.getMessage());
        }

        // Notification nested class from Trackable interface
        section("Trackable.Notification (nested interface class)");
        Trackable.Notification notif =
            new Trackable.Notification.EmailNotification("sneh@upes.ac.in");
        notif.sendAlert("Your shuttle has been booked.");
        transport.trackStatus();

        // SecureAccess marker interface check
        section("SecureAccess Marker Interface");
        StudentUser su = new StudentUser("U001", "Karan", 3);
        FacultyUser fu = new FacultyUser("U002", "Dr. Brahmbhatt", "ECE");
        User[] users = {su, fu};
        for (User u : users) {
            u.accessService();
            if (u instanceof SecureAccess) {
                System.out.println("  -> " + u.getName() + " has SecureAccess privileges.");
            }
        }

        // ─────────────────────────────────────────────────────────────────────
        // PART 3: Exceptions, Strings, Wrappers
        // ─────────────────────────────────────────────────────────────────────
        banner("PART 3 - Exceptions, String Processing, Wrappers");

        // Invalid roll number -> InvalidUserException
        section("InvalidUserException");
        try {
            Student bad = new Student("S099", "Bad User", "bad@x.com", "INVALID", 1);
        } catch (InvalidUserException e) {
            System.out.println("Caught InvalidUserException: " + e.getMessage());
        } finally {
            System.out.println("finally block always runs.");
        }

        // SeatNotAvailableException with multiple catch
        section("SeatNotAvailableException + multiple catch");
        try {
            transport.bookSeat("Veer", 999);   // too many seats
        } catch (SeatNotAvailableException e) {
            System.out.println("Caught SeatNotAvailableException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Caught generic Exception: " + e.getMessage());
        } finally {
            System.out.println("Booking attempt finalized.");
        }

        // PaymentFailedException propagating to calling method
        section("PaymentFailedException - propagation");
        try {
            processPaymentDemo(transport, -50.0);   // propagates up from method
        } catch (PaymentFailedException e) {
            System.out.println("Caught in main: PaymentFailedException: " + e.getMessage());
        }

        // InvalidServiceRequestException
        section("InvalidServiceRequestException");
        String requested = "helicopter";
        try {
            if (!StringUtil.isValidService(requested)) {
                throw new InvalidServiceRequestException(
                    "Service '" + requested + "' is not available on campus.");
            }
        } catch (InvalidServiceRequestException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // String operations
        section("String Operations");
        String complaint = "  My laptop charger is broken.  ";
        System.out.println("Original    : \"" + complaint + "\"");
        System.out.println("trim()      : \"" + complaint.trim() + "\"");
        System.out.println("length()    : " + complaint.trim().length());
        System.out.println("charAt(3)   : " + complaint.trim().charAt(3));
        System.out.println("contains()  : " + complaint.contains("broken"));
        System.out.println("indexOf('l'): " + complaint.indexOf('l'));
        System.out.println("substring() : " + complaint.trim().substring(3, 9));
        System.out.println("replace()   : " + complaint.trim().replace("broken", "damaged"));
        System.out.println("equals()    : " + "admin".equals("ADMIN"));
        System.out.println("equalsIgnoreCase: " + "admin".equalsIgnoreCase("ADMIN"));
        System.out.println("Masked ID   : " + StringUtil.maskUserId("STU-12345"));
        System.out.println("Booking summary:\n" + StringUtil.buildBookingSummary("Karan", "Transport", 2, 40.0));
        System.out.println("Complaint   : " + StringUtil.formatComplaint("S001", complaint));
        StringUtil.demonstrateStringConversions("CampusPass");

        // CSV service request parsing
        String csv = "transport,library,lab";
        String[] services = StringUtil.parseServiceRequest(csv);
        System.out.print("Parsed CSV  : ");
        for (String sv : services) System.out.print("[" + sv + "] ");
        System.out.println();

        // Wrapper class and autoboxing
        StringUtil.demonstrateWrappers();

        // ─────────────────────────────────────────────────────────────────────
        // PART 4: Nested Classes
        // ─────────────────────────────────────────────────────────────────────
        banner("PART 4 - Nested Classes");

        section("Static Nested Class - AdminPanel.Report");
        AdminPanel.Report report = new AdminPanel.Report("April Usage Report");
        report.addBookings(42);
        report.addComplaints(7);
        report.printReport();

        section("Inherited Nested Class - ChildReport extends AdminPanel.Report");
        ChildReport childReport = new ChildReport("Semester Report", "Sem 3");
        childReport.addBookings(18);
        childReport.addComplaints(3);
        childReport.printReport();

        section("Non-static Inner Class - SupportDesk.Ticket");
        SupportDesk desk = new SupportDesk("Desk-A");
        SupportDesk.Ticket t1_ = desk.createTicket("Srishti Soni", "WiFi not working in Lab 3");
        SupportDesk.Ticket t2_ = desk.createTicket("Sneha Jain", "Library card not scanning");
        t1_.display();
        t2_.display();
        t1_.resolve();
        t1_.display();

        section("Local Inner Class - AdminPanel.generateTemporaryPass()");
        AdminPanel admin = new AdminPanel("ADM-01", "Dr. Verma");
        admin.generateTemporaryPass("Rahul (Visitor)");

        section("Anonymous Inner Class - AdminPanel.scheduleAudit()");
        admin.scheduleAudit();

        // ─────────────────────────────────────────────────────────────────────
        // PARTS 5–8: Threads, ResourcePool, Exceptions in Threads
        // ─────────────────────────────────────────────────────────────────────
        banner("PARTS 5-8 - Threads, Synchronization, Exceptions");

        ResourcePool shuttlePool = new ResourcePool("ShuttlePool", 3);

        // ThreadGroup for monitoring thread (Part 6)
        ThreadGroup campusGroup = new ThreadGroup("CampusThreadGroup");

        // MonitoringThread (in ThreadGroup)
        MonitoringThread monitor = new MonitoringThread(shuttlePool, 400, campusGroup);
        monitor.start();

        // ComplaintThread — runs until stopped
        ComplaintThread complaintThread = new ComplaintThread("S001", 600);
        complaintThread.start();

        // StudentThreads (extend Thread)
        StudentThread st1 = new StudentThread("Jeh",  shuttlePool, 2);
        StudentThread st2 = new StudentThread("Srish",  shuttlePool, 2);
        StudentThread st3 = new StudentThread("Karan",  shuttlePool, 1);

        // FacultyThreads (implement Runnable)
        Thread ft1 = new Thread(new FacultyThread("Dr.Sharma", shuttlePool, 1), "FacultyThread-Sharma");
        Thread ft2 = new Thread(new FacultyThread("Dr.Soni",  shuttlePool, 1), "FacultyThread-Mehta");

        // AdminThread — adds resources after delay
        AdminThread adminThread = new AdminThread(shuttlePool, 5, 700);

        // Payment threads (Part 7)
        PaymentThread pay1 = new PaymentThread("Jeh",  500.0);
        PaymentThread pay2 = new PaymentThread("Srish",  -200.0);  // will fail
        PaymentThread pay3 = new PaymentThread("Karan",  0.0);     // will fail
        PaymentThread payBad = PaymentThread.createBadThread("Ghost"); // uncaught demo

        section("Starting all threads");
        st1.start();
        st2.start();
        st3.start();
        ft1.start();
        ft2.start();
        adminThread.start();
        pay1.start();
        pay2.start();
        pay3.start();
        payBad.start();

        // join() — wait for resource-related threads before stopping monitor
        st1.join();
        st2.join();
        st3.join();
        ft1.join();
        ft2.join();
        adminThread.join();
        pay1.join();
        pay2.join();
        pay3.join();
        payBad.join();

        // Gracefully stop looping threads (Part 6 flag pattern)
        section("Graceful thread stop (flag pattern - not Thread.stop())");
        complaintThread.stopGracefully();
        monitor.stopGracefully();
        complaintThread.join(1500);
        monitor.join(1500);

        // ThreadGroup info (Part 6)
        section("ThreadGroup info");
        System.out.println("ThreadGroup name     : " + campusGroup.getName());
        System.out.println("Active threads       : " + campusGroup.activeCount());

        // Part 8: Logging-oriented summary
        section("Part 8 - Final State Log");
        System.out.println("[LOG] ShuttlePool final state:");
        System.out.println("      Available : " + shuttlePool.getAvailable());
        System.out.println("      Booked    : " + shuttlePool.getBooked());

        // ─────────────────────────────────────────────────────────────────────
        // DEADLOCK demo (Final Scenario)
        // ─────────────────────────────────────────────────────────────────────
        banner("FINAL SCENARIO - Deadlock Demo + Fix");
        DeadlockDemo.runDeadlockExample();
        DeadlockDemo.runCorrectedExample();

        banner("ALL PARTS COMPLETE");
        System.out.println("Smart Campus Service Management System - simulation finished.");
    }

    /**
     * Part 3: Exception propagation demonstration.
     * Throws PaymentFailedException which propagates to main().
     */
    static void processPaymentDemo(TransportService ts, double amount)
            throws PaymentFailedException {
        System.out.println("[processPaymentDemo] Attempting payment of Rs " + amount);
        if (amount < 0) {
            throw new PaymentFailedException(
                "Propagated: cannot process Rs " + amount);
        }
    }
}
