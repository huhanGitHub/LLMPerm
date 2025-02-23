import java.util.TimeZone;

public class TimeZone_setDefault {

    public void test_TimeZone_setDefault() {
        // Get the default TimeZone
        TimeZone defaultTimeZone = TimeZone.getDefault();

        // Print the default TimeZone
        System.out.println("Default TimeZone: " + defaultTimeZone.getID());

        // Set the default TimeZone to "America/Los_Angeles"
        TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));

        // Get the new default TimeZone
        TimeZone newDefaultTimeZone = TimeZone.getDefault();

        // Print the new default TimeZone
        System.out.println("New Default TimeZone: " + newDefaultTimeZone.getID());

        // Verify that the default TimeZone has been changed successfully
        if (!newDefaultTimeZone.getID().equals(defaultTimeZone.getID())) {
            System.out.println("Default TimeZone successfully changed");
        } else {
            System.out.println("Failed to change default TimeZone");
        }
    }
}