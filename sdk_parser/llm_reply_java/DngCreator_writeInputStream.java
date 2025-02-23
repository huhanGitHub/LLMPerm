public class DngCreator_writeInputStream {
    public void test_DngCreator_writeInputStream() {
        //Initialize
        ImageReader reader = ImageReader.newInstance(640, 480, ImageFormat.RAW_SENSOR, 2);
        CaptureResult result = null;
        Image image = reader.acquireNextImage();
        
        DngCreator dngCreator = new DngCreator(cameraCharacteristics, result);
        FileOutputStream outputStream = null;

        try {
            //Create file
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String imageFileName = "JPEG_" + timeStamp + ".dng";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            File imageFile = File.createTempFile(imageFileName,".dng", storageDir);
            
            //Write to file
            outputStream = new FileOutputStream(imageFile);
            dngCreator.writeImage(outputStream, image);
            
            //Your image.dng is created.
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Release resources
            if (image != null) {
                image.close();
            }
            
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}