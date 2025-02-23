public class ControlsProviderService_createPublisherFor {
    
    public static void test_ControlsProviderService_createPublisherFor() {
        // Prepare creation
        List<String> controlIds = new ArrayList<>();
        controlIds.add("control1");
        controlIds.add("control2");
        
        // Start service
        Intent intent = new Intent(context, TestControlsProviderService.class);
        context.startService(intent);

        // Usage example
        TestControlsProviderService testService = new TestControlsProviderService();
        Control.PipeDataPublisher publisher = testService.onCreatePublisherFor(controlIds);

        if (publisher != null) {
            Log.i("ControlsProviderService", "Publisher created successfully!");
        } else {
            Log.e("ControlsProviderService", "Failed to create publisher");
        }
    }
}