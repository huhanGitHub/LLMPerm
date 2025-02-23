public class BitmapRegionDecoder_newInstance {
    public void test_BitmapRegionDecoder_newInstance() {
        // Load the input stream from a bitmap file (you will need to provide the correct data path)
        InputStream is = null;
        try {
            is = getResources().getAssets().open("path_to_your_image.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (is != null) {
            try {
                // Initialise the BitmapRegionDecoder with the input stream
                BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(is, false);

                // Define the region to decode as the left top quarter of the image
                int width = decoder.getWidth();
                int height = decoder.getHeight();
                Rect rect = new Rect(0, 0, width / 2, height / 2);

                // Decode the new region of the image and get the result as a bitmap
                Bitmap regionBitmap = decoder.decodeRegion(rect, new BitmapFactory.Options());

                // Use the Bitmap as needed, for example set it on ImageView
                // imageView.setImageBitmap(regionBitmap);

                // Close the input stream and the decoder
                is.close();
                decoder.recycle();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}