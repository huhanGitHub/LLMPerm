import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.Gesture;
import android.gesture.Prediction;
import android.content.Context;

import java.io.File;
import java.util.ArrayList;

public class GestureLibraries_fromPrivateFile {
    public void test_GestureLibraries_fromPrivateFile(Context context) {
        // Define the filename, located in the application's private file directory
        String fileName = "gestures";

        // Get the application's private file directory
        File file = context.getFileStreamPath(fileName);

        // Create a gesture library from a private file
        GestureLibrary gestureLibrary = GestureLibraries.fromPrivateFile(context, fileName);

        // Load the library
        boolean isLoadSuccess = gestureLibrary.load();

        if(isLoadSuccess){
            System.out.println("Gesture library successfully loaded.");

            // Consider hypothetical "exampleGesture" recognized through the android.gesture.GestureOverlayView
            Gesture exampleGesture = new Gesture();

            // Assuming the library is not empty and contains gestures,
            // identify the gesture in the library that most closely resembles "exampleGesture"
            ArrayList<Prediction> predictions = gestureLibrary.recognize(exampleGesture);
            
            if(predictions.size() > 0){
                Prediction bestPrediction = predictions.get(0);
                if(bestPrediction.score > 1.0){
                    //Gesture is present in library with fair confidence, print gesture name to console
                    System.out.println(bestPrediction.name);
                }
                else{
                    //Gesture was not recognized confidently
                    System.out.println("Gesture not recognized confidently.");
                }
            }
            else{
                System.out.println("No gestures recognized.");
            }
        }
        else{
            System.out.println("Failed to load gesture library.");
        }
    }
}