public class CallRedirectionService_placeCall {
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void test_CallRedirectionService_placeCall() {
        Uri phoneNumber = Uri.parse("tel:1234567890");
    
        TelecomManager telecomManager = (TelecomManager) this.getSystemService(Context.TELECOM_SERVICE);
    
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Request for CALL_PHONE permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL_PHONE);
            return;
        }
    
        PhoneAccountHandle defaultOutgoingPhoneAccount = telecomManager.getDefaultOutgoingPhoneAccount("tel");
    
        Bundle extras = new Bundle();
        extras.putParcelable(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE, defaultOutgoingPhoneAccount);
    
        telecomManager.placeCall(phoneNumber, extras);
    }
}