public class MmsManager_downloadMultimediaMessage {
    
    public void test_MmsManager_downloadMultimediaMessage() {
        try {
            MmsManager mmsManager = MmsManager.getInstance(); //Assuming singleton pattern
            Uri mmsUri = Uri.parse("content://mms-sms/conversations/"); //Get Uri of MMS
            
            boolean isDownloaded = mmsManager.downloadMultimediaMessage(this, mmsUri);

            if (isDownloaded) {
                System.out.println("MMS downloaded successfully.");
            } else {
                System.out.println("MMS download failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}