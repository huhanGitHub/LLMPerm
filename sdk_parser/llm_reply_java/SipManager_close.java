public class SipManager_close {
    public void test_SipManager_close(){
        android.net.sip.SipManager sipManager;
        android.net.sip.SipProfile sipProfile;

        try {
            //initialize the SipManager
            sipManager = android.net.sip.SipManager.newInstance(this);

            //define the SipProfile
            SipProfile.Builder builder = new SipProfile.Builder("user", "realm");
            builder.setPassword("password");
            sipProfile = builder.build();

            //open the SipManager with the defined profile
            sipManager.open(sipProfile);

            if(sipManager != null){
                System.out.println("SipManager opened successfully");
            }

            //now, close the SipManager
            sipManager.close(sipProfile.getUriString());
            if(sipManager.isOpened(sipProfile.getUriString()) == false){
                System.out.println("SipManager closed successfully");
            }
        } catch (Exception e) {
            if(sipManager != null) {
                try {
                    sipManager.close(sipProfile.getUriString());
                } catch (Exception e1) {
                    System.out.println("Error in closing SipManager: " + e1.getMessage());
                }
            }
            System.out.println("Error in SipManager: " + e.getMessage());
        }
    }
}