public class TouchInteractionController_performClick {
    public void test_TouchInteractionController_performClick(float x, float y) {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();

        // Create a "down" event
        MotionEvent downEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, 0);

        // Create an "up" event
        MotionEvent upEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x, y, 0);

        // Perform the "click"
        if(touchInteractionController != null) {
            touchInteractionController.injectEvents(downEvent, upEvent, (completed) -> {
                if (completed) Log.d("TAG","Click performed");
                else Log.d("TAG","Click not performed");
            });
        } else Log.d("TAG", "TouchInteractionController not available");
    }
}