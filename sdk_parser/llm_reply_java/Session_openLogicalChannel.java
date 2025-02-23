import android.se.omapi.Channel;
import android.se.omapi.Session;
import android.util.Log;

public class Session_openLogicalChannel {

    public void test_Session_openLogicalChannel(Session session) {
        byte[] aid = { 
            (byte) 0xA0, 
            (byte) 0x00, 
            (byte) 0x00, 
            (byte) 0x00, 
            (byte) 0x62,
            (byte) 0x03, 
            (byte) 0x01, 
            (byte) 0x05, 
            (byte) 0x00, 
            (byte) 0x00
        }; 
        // This is an ISO 7816-4 command. It tells the card to return FCI Template.
        
        try {
            Channel channel = session.openLogicalChannel(aid);
            // Checking if the channel is open
            if (channel != null) {
                Log.d("OpenLogicalChannel", "Open Logical Channel Successful");
                // TODO: Add what you want to do with the channel here
                
                // Always remember to close the channel once done
                channel.close();
            }
            else {
                Log.e("OpenLogicalChannel", "Failed to open logical channel, null channel returned");
            }
        } catch (Exception e) {
            Log.e("OpenLogicalChannel", "OpenLogicalChannel error: " + e.toString());
        }
    }
}