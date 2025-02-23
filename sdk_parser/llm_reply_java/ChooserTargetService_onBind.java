public class ChooserTargetService_onBind {
    
    public void test_ChooserTargetService_onBind() {
        Intent intent = new Intent(this, ChooserTargetService.class);
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.d("Test", "Service successfully bound");
                assertNotNull(iBinder); // If iBinder is not null, it means Bind was successful.
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.d("Test", "Service disconnected");
            }
        };

        // Use bindService method
        boolean isBound = bindService(intent, connection, Context.BIND_AUTO_CREATE);
        if(isBound) {
            Log.d("Test", "Service is bound, now you can check onBind in your ChooserTargetService");
        } else {
            Log.d("Test", "Service not bound, check your ChooserTargetService onBind method.");
        }
    }
}