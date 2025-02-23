public class OnReceiveContentListener_onReceiveContent {
    public void test_OnReceiveContentListener_onReceiveContent() {
        View view = findViewById(R.id.my_view);
        
        view.setOnReceiveContentListener(new View.OnReceiveContentListener() {
            @Override
            public ContentCaptureSession newSession(ContentCaptureContext contentCaptureContext) {
                return null;
            }

            @Override
            public void onContentReceived(@NonNull View view, @NonNull ContentInfo contentInfo) {

                int operation = contentInfo.getFlags();
                
                if (operation == ContentInfo.FLAG_CONVERT_TO_PLAIN_TEXT) {
                    CharSequence text = contentInfo.getClip().getItemAt(0).getText();
                    Log.d("ContentListener", "Received text: " + text);
                }    
                
                contentInfo.releasePermission();
            }
        });
    }
}