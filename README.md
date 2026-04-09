# Smart Campus Service Management System
## Design Report

---

### Overview
A Java-based simulation of a university campus service management system.
Users (students, faculty) request services (transport, library, lab, complaints)
concurrently while shared resources are safely managed using multithreading.

---

### Package Structure

| Package          | Contents                                                  |
|------------------|-----------------------------------------------------------|
| campus.main      | MainApp — entry point, runs all parts                     |
| campus.users     | Person, Student, Faculty, User (abstract), StudentUser,  |
|                  | FacultyUser, SecureAccess (marker interface)              |
| campus.services  | Service, Bookable, Payable, Trackable (with nested        |
|                  | Notification interface), TransportService,                |
|                  | LibraryService, PremiumLabService, UniversityRules (final)|
| campus.exceptions| InvalidUserException, SeatNotAvailableException,          |
|                  | InvalidServiceRequestException, PaymentFailedException    |
| campus.util      | StringUtil — string processing, wrapper class demos       |
| campus.threads   | ResourcePool, StudentThread, FacultyThread, AdminThread,  |
|                  | ComplaintThread, MonitoringThread, PaymentThread,         |
|                  | DeadlockDemo                                              |
| campus.inner     | AdminPanel (static nested Report, local class, anonymous  |
|                  | Runnable), SupportDesk (non-static inner Ticket),         |
|                  | ChildReport (extends AdminPanel.Report)                   |

---

### Java Concepts Used — Part Mapping

#### Part 1 — Basic Classes
- `private/protected/public` fields across Person, Student, Faculty
- `this` keyword in all parameterized constructors and setters
- **Method overloading**: `requestService()`, `requestService(String)`, `requestService(String, int)` in both Student and Faculty
- Arrays: `Person[] people` used in MainApp to store mixed user types
- Control flow: `if-else`, `switch`, `for`, `while`, `break`, `continue` all demonstrated in MainApp

#### Part 2 — Inheritance & Interfaces
- **Abstract class User** with abstract `accessService()` → implemented by StudentUser and FacultyUser
- **Interfaces**: Bookable, Payable, Trackable each implemented by appropriate service classes
- **Constructor chaining**: `super(serviceId, serviceName)` in every service subclass
- **Method overriding**: `showServiceDetails()`, `trackStatus()`, etc.
- **final class**: `UniversityRules` — cannot be subclassed; contains policy constants
- **final method**: `generateReceipt()` inside UniversityRules — cannot be overridden
- **Marker interface**: `SecureAccess` — no methods; presence grants special privileges
- **Upcasting**: `Service svc = transport` (TransportService stored as Service)
- **Downcasting**: `(TransportService) svc` with `instanceof` check
- **Object class methods**: `toString()` overridden in Person, Student, Faculty; `equals()` overridden in Person; `clone()` implemented in Student
- **Nested interface**: `Trackable.Notification` interface with concrete inner class `EmailNotification`

#### Part 3 — Exceptions, Strings, Wrappers
- **Custom exceptions**: All 4 thrown and caught with meaningful messages
- **throw/throws**: `bookSeat()` uses `throws SeatNotAvailableException`; Student constructor uses `throw new InvalidUserException()`
- **try-catch-finally**: Shown for every exception type
- **Multiple catch blocks**: SeatNotAvailableException catch + generic Exception catch
- **Exception propagation**: `processPaymentDemo()` throws PaymentFailedException up to main()
- **Wrapper classes**: `Integer.parseInt()`, `Double.parseDouble()`, `Integer.valueOf()`, `Boolean.valueOf()`
- **Autoboxing/unboxing**: `Integer autoBoxed = 100` and `int unboxed = autoBoxed`
- **StringBuffer**: Used in `formatComplaint()` (thread-safe)
- **StringBuilder**: Used in `buildBookingSummary()` and `maskUserId()`
- **All String methods**: `equals()`, `equalsIgnoreCase()`, `length()`, `substring()`, `charAt()`, `contains()`, `indexOf()`, `replace()`, `trim()`
- **char array ↔ String**: `toCharArray()` and `new String(chars)`
- **byte array**: `getBytes()` on a String

#### Part 4 — Nested Classes

| Type | Class | Why this form? |
|------|-------|----------------|
| Static nested | `AdminPanel.Report` | Report doesn't need AdminPanel instance state; can be used standalone as `AdminPanel.Report` |
| Non-static inner | `SupportDesk.Ticket` | Ticket is meaningless without a SupportDesk; holds implicit outer reference, increments `ticketCounter` on enclosing object |
| Local inner | `TemporaryPass` inside `generateTemporaryPass()` | Only needed within that method's scope; encapsulates pass logic without polluting the outer class |
| Anonymous inner | `Runnable` inside `scheduleAudit()` | One-off task; no reuse needed; inline definition keeps intent clear |
| Nested inside interface | `Notification.EmailNotification` inside `Trackable.Notification` | Groups the notification contract and its default implementation logically |
| Inherit nested class | `ChildReport extends AdminPanel.Report` | Proves static nested classes can be subclassed from outside the enclosing class |

#### Parts 5–6 — Threads & Safe Termination
- **Two creation methods**: StudentThread (extends Thread), FacultyThread (implements Runnable)
- **Thread roles**: Student, Faculty, Admin, Complaint, Monitoring threads
- **`synchronized` methods**: All ResourcePool methods are synchronized
- **`wait()`**: Called inside `requestResource()` when resources are insufficient
- **`notify()`**: Called inside `releaseResource()` to wake one waiter
- **`notifyAll()`**: Called inside `addResources()` to wake all waiters
- **`sleep()`**: Used in all thread `run()` methods to simulate work/delay
- **`join()`**: MainApp waits for all worker threads before stopping monitor/complaint threads
- **Graceful stop**: `ComplaintThread` and `MonitoringThread` use `volatile boolean running` flag; `stopGracefully()` sets it to false
- **Why not `Thread.stop()`**: Forcibly releases all held monitors → corrupts shared state (documented in AdminThread.java)
- **`setName()`**: Done via `super("ThreadName")` in all Thread subclasses
- **`currentThread()`**: Used in MonitoringThread.run() to get name
- **`ThreadGroup`**: CampusThreadGroup created in MainApp; MonitoringThread added to it via constructor

#### Part 7 — Threads & Exceptions
- **Exception in run()**: PaymentThread catches `PaymentFailedException` inside `run()`
- **`throw`/`throws` inside thread**: `processPayment()` declares `throws PaymentFailedException`
- **Uncaught exception**: `PaymentThread.createBadThread()` throws RuntimeException from `run()`
- **UncaughtExceptionHandler**: Set in PaymentThread constructor via `setUncaughtExceptionHandler()`

#### Part 8 — Debugging & Logging
- All threads print their name via `Thread.currentThread().getName()` or `getName()`
- ResourcePool logs available/booked counts before and after every operation
- Wait and notify states are explicitly logged
- Payment success and failure outcomes logged with amounts
- Final state summary printed after all threads complete
- DeadlockDemo prints detection and resolution steps

---

### Concurrency Design
`ResourcePool` uses Java's intrinsic lock (synchronized + wait/notify pattern):
- Requester calls `requestResource()` → if insufficient, calls `wait()` releasing the lock
- Releaser calls `releaseResource()` → restores count, calls `notify()` to wake one waiter
- Admin calls `addResources()` → calls `notifyAll()` to wake all waiters simultaneously
- This prevents busy-waiting and ensures CPU efficiency while maintaining correctness

### Deadlock Analysis
- **Cause**: Thread-1 locks A then waits for B; Thread-2 locks B then waits for A — circular wait
- **Fix**: Enforce a global lock acquisition order (always A before B) across all threads — no circular dependency can form
