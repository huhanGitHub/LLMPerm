public class GestureLibraries_save {
    public void test_GestureLibraries_save() {
        // Create a GestureLibrary object
        GestureLibrary gestureLibrary = GestureLibraries.fromFile(getExternalFilesDir(null) + "/" + "mygesture");

        // Create a new Gesture object
        Gesture gesture = new Gesture();

        // Add GestureStroke to Gesture
        ArrayList<GesturePoint> gesturePoints = new ArrayList<>();
        gesturePoints.add(new GesturePoint(0f,0f, 0));
        gesturePoints.add(new GesturePoint(1f,1f, 1));
        GestureStroke stroke = new GestureStroke(gesturePoints);
        gesture.addStroke(stroke);

        // Add Gesture to Gesture Library
        gestureLibrary.addGesture("My gesture", gesture);

        // Save GestureLibrary
        boolean isGestureSaved = gestureLibrary.save();

        // Check whether gesture is saved successfully
        if(isGestureSaved){
            Toast.makeText(this, "Gesture saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save gesture", Toast.LENGTH_SHORT).show();
        }
    }
}