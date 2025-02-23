public class NfcA_transceive {
    public void test_NfcA_transceive(Tag myTag) {
        NfcA nfcA = NfcA.get(myTag);
  
        // Ensure you perform NFC communication on a separate thread than the UI one
        // to avoid ANRs (Application Not Responding) 
        new Thread(() -> {
            try {
                // Connect to the discovered NfcA tag
                nfcA.connect();
      
                // Define a simple command (all zeros)
                byte[] command = new byte[16];
  
                // Send the command and receive a response
                byte[] response = nfcA.transceive(command);
      
                Log.i("NfcATest", "Command sent, response received.");
                // Do something with the response...
      
                // Make sure to close the connection when done
                nfcA.close();
            } catch (IOException e) {
                Log.e("NfcATest", "Error communicating with NfcA tag", e);
            }
        }).start();
    }
}