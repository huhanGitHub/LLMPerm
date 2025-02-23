public class GestureLibraries_fromFile {
    public void test_GestureLibraries_fromFile() {
        // Specify the path of the gesture file in the app's assets folder
        String gestureFilePath = "file:///android_asset/gestures";

        // Load the gesture library from the specified file
        GestureLibrary gestureLibrary = GestureLibraries.fromFile(gestureFilePath);

        // Load the gesture library
        if (gestureLibrary.load()) {
            // If the library loads successfully, print the count of the gestures
            int gestureCount = gestureLibrary.getGestureEntries().size();
            Log.d("Gesture_Test", "Gesture library loaded successfully with " 
                + gestureCount + " gestures.");
        } else {
            // If the library fails to load, print an error message
            Log.d("Gesture_Test", "Failed to load gesture library from file.");
        }
    }
}