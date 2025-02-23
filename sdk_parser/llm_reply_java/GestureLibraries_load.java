public class GestureLibraries_load {
    public void test_GestureLibraries_load() {
        try {
            // Path of the gesture file
            // This example uses a file named gesture in the application's external files directory
            String path = this.getExternalFilesDir(null).getPath() + "/gestures";

            // Load the gesture library from a file
            GestureLibrary library = GestureLibraries.fromFile(path);

            if (library != null) {
                // If the gesture library was successfully loaded
                // Load all gestures from the gesture library
                boolean loadedGestures = library.load();

                if (loadedGestures) {
                    Toast.makeText(this, "Gesture library was successfully loaded", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Could not load gestures from the library", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Gesture library could not be created", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error while creating/loading Gesture library: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}