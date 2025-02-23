public class CarrierMessagingService_sendMms {

    @SuppressLint("NewApi")
    public void test_CarrierMessagingService_sendMms() {
        // Assume this is an Activity (and context)
        Context context = this; 

        // We need the SEND_SMS permission
        String permission = Manifest.permission.SEND_SMS;
        int grant = ActivityCompat.checkSelfPermission(context, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }

        // Message config depending on your requirement.
        Uri contentUri = Uri.parse("file://path/to/mms_content");
        Uri attachUri = Uri.parse("file://path/to/image.jpg");

        MmsMessage mmsMessage = new MmsMessage("1234567890", "Hello!", Arrays.asList(contentUri));

        // Attaching the image to the message.
        mmsMessage.addPart(
                new Part(
                        MediaStore.Images.Media.DISPLAY_NAME,
                        "image/jpeg",
                        attachUri
                )
        );

        // Getting system service CARRIER_MESSAGING_SERVICE
        CarrierMessagingService carrierMessagingService = context.getSystemService(CarrierMessagingService.class);

        if (carrierMessagingService != null) {
            carrierMessagingService.sendMms(mmsMessage, PendingIntent.getBroadcast(
            context, 0, new Intent(ACTION_SEND_MESSAGE), 0),
            new CarrierMessagingService.SendMmsResult() {
                @Override
            public void onSendMmsComplete(int resultCode, Uri mmsId) {
                if (resultCode == CarrierMessagingService.SEND_STATUS_OK) {
                    // MMS sent successfully
                    Log.i("MMS Test", "MMS sent successfully!");
                } else {
                    // Failure in sending MMS
                    Log.e("MMS Test", "Failure in sending MMS!");
                }
            }
            });
        } else {
            Log.e("MMS Test", "CarrierMessagingService is null!");
        }
    }
}