public class DngCreator_writeByteBuffer {
    public void test_DngCreator_writeByteBuffer() {
        File file = new File(getExternalFilesDir(null), "pic.dng");
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(file);
            DngCreator dngCreator = new DngCreator(mCharacteristics, mCaptureResult);
            dngCreator.writeImage(output, mRawImage);
            Toast.makeText(mContext, "Saved: " + file, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mRawImage.close();
            if (null != output) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}