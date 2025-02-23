import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.content.Intent;

public class Tag_getTagService {

  public void test_Tag_getTagService(Context context) {
    // Create an intent with the ACTION_NDEF_DISCOVERED action
    Intent intent = new Intent(NfcAdapter.ACTION_NDEF_DISCOVERED);

    // Create a new Tag object from the intent
    if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
      Tag tagFromIntent = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

      // Now we can use the tag obtained from the intent to get the NfcAdapter instance
      NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);

      if(nfcAdapter != null && nfcAdapter.isEnabled()){
        // Get the Tag Service from the nfcAdapter
        String[] techList = tagFromIntent.getTechList();
        for (String tech : techList) {
          System.out.println("Detected tech = " + tech);
        }

      } else {
        System.out.println("NFC feature is not enabled on your device");
      }

    } else {
      System.out.println("NDEF not discovered");
    }
  }
}