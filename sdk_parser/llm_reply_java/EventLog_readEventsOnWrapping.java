import android.util.EventLog;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventLog_readEventsOnWrapping {
    
    public void test_EventLog_readEventsOnWrapping() {
        
        // create a new array list
        List<EventLog.Event> events = new ArrayList<>();

        try {
            
            // read events
            int logBuffer = EventLog.readEvents(new int[] { 12345 }, events); // Replace 12345 with actual EventLog tag

            // iterate through events and process
            for (EventLog.Event event : events) {
                
                // retrieve the data in the event
                Object[] data = (Object[]) event.getData();
                
                // process the data (just a printout in this case)
                for (Object o : data) {
                    Log.i("EventLogReadEvents", "Data: " + o);
                }
            }

        } catch (IOException e) {
            
            // print any error messages
            Log.e("EventLogReadEvents", "Error reading events", e);
        }
    }
}