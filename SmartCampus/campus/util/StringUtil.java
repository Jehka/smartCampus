package campus.util;

/**
 * Part 3: String processing utility for the campus system.
 * Demonstrates: equals/equalsIgnoreCase, length, substring, charAt, contains,
 * indexOf, replace, trim, char array, byte array, StringBuffer, StringBuilder.
 */
public class StringUtil {

    /**
     * Masks a user ID so only last 3 characters are visible.
     * E.g. "STU-12345" -> "******345"
     */
    public static String maskUserId(String userId) {
        if (userId == null || userId.length() <= 3) return userId;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < userId.length() - 3; i++) sb.append('*');
        sb.append(userId.substring(userId.length() - 3));
        return sb.toString();
    }

    /**
     * Format a booking summary using StringBuilder.
     */
    public static String buildBookingSummary(String userName, String service,
                                             int seats, double fee) {
        StringBuilder sb = new StringBuilder();
        sb.append("Booking Summary")
          .append("\n  User   : ").append(userName)
          .append("\n  Service: ").append(service)
          .append("\n  Seats  : ").append(seats)
          .append("\n  Fee    : Rs ").append(fee);
        return sb.toString();
    }

    /**
     * Format a complaint message using StringBuffer (thread-safe).
     */
    public static String formatComplaint(String userId, String complaint) {
        StringBuffer buf = new StringBuffer();
        buf.append("[COMPLAINT] User: ").append(userId.trim())
           .append(" | Message: ").append(complaint.trim().replace("  ", " "));
        return buf.toString();
    }

    /**
     * Parse a comma-separated service request string.
     * E.g., "transport,library,lab" -> array of service names
     */
    public static String[] parseServiceRequest(String csv) {
        return csv.trim().split(",");
    }

    /**
     * Compare role names (login check), case-insensitive.
     */
    public static boolean roleMatches(String inputRole, String expectedRole) {
        return inputRole.trim().equalsIgnoreCase(expectedRole.trim());
    }

    /**
     * Validate service name using contains/indexOf.
     */
    public static boolean isValidService(String serviceName) {
        String[] valid = {"transport", "library", "lab", "complaint"};
        String lower = serviceName.toLowerCase();
        for (String v : valid) {
            if (lower.contains(v)) return true;
        }
        return false;
    }

    /** Demonstrate char array and byte array conversions. */
    public static void demonstrateStringConversions(String input) {
        System.out.println("\n[StringUtil] Conversions for: \"" + input + "\"");

        // String -> char array
        char[] chars = input.toCharArray();
        System.out.print("  Chars  : ");
        for (char c : chars) System.out.print(c + " ");
        System.out.println();

        // char array -> String
        String fromChars = new String(chars);
        System.out.println("  Back   : " + fromChars);

        // String -> byte array
        byte[] bytes = input.getBytes();
        System.out.print("  Bytes  : ");
        for (byte b : bytes) System.out.print(b + " ");
        System.out.println();

        // Misc operations
        System.out.println("  Length : " + input.length());
        System.out.println("  charAt(0): " + input.charAt(0));
        System.out.println("  indexOf('a'): " + input.indexOf('a'));
        if (input.length() > 3)
            System.out.println("  substring(0,3): " + input.substring(0, 3));
    }

    /** Wrapper class demonstrations. */
    public static void demonstrateWrappers() {
        System.out.println("\n[StringUtil] Wrapper class demonstrations:");

        // parseInt / parseDouble
        String numStr  = "42";
        String dblStr  = "99.5";
        int    parsed  = Integer.parseInt(numStr);          // String -> int
        double parsedD = Double.parseDouble(dblStr);        // String -> double

        // valueOf
        Integer boxed   = Integer.valueOf(parsed);          // autoboxing via valueOf
        Boolean flag    = Boolean.valueOf("true");

        // Autoboxing and unboxing
        Integer autoBoxed = 100;                            // autoboxing
        int     unboxed   = autoBoxed;                      // unboxing

        System.out.println("  parseInt(\"42\")       = " + parsed);
        System.out.println("  parseDouble(\"99.5\") = " + parsedD);
        System.out.println("  Integer.valueOf(42)  = " + boxed);
        System.out.println("  Boolean.valueOf(true)= " + flag);
        System.out.println("  Autoboxed            = " + autoBoxed);
        System.out.println("  Unboxed              = " + unboxed);
    }
}
