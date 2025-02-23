public class DrmUtils_writeToFile {

    public void test_DrmUtils_writeToFile() {
        File file = new File(getFilesDir(), "testFile.txt");

        String content = "This is the content that will be written to the file.";

        FileOutputStream fos = null;
        
        try {
            fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();

            Toast.makeText(this, 
                "Successfully written to " + file.getAbsolutePath(),
                Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File getFilesDir() {
        // Replace this method implementation to suit your app context
        return new File("/path/to/app/directory");
    }
    
    private void Toast.makeText(DrmUtils_writeToFile drmUtils_writeToFile, String message, int lengthShort) {
        // Mock Toast method for demonstration, in real scenario use Android's Toast class
        System.out.println(message);
    }
}