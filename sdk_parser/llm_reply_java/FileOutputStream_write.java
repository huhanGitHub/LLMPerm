public class FileOutputStream_write {
    public void test_FileOutputStream_write() {
        // the name of the file to open
        String fileName = "testfile.txt";
        
        FileOutputStream fos = null;
        try {
            // Open the file for writing
            fos = new FileOutputStream(fileName);
            
            // The string to write to the file
            String contentToWrite = "This is a test content!";
            
            // Convert the String content to bytes
            byte[] byteArray = contentToWrite.getBytes();
            
            // Write bytes to file
            fos.write(byteArray);
            
            // close the file output stream
            fos.close();
            
            // confirmation message
            Toast.makeText(this, "Successfully written to the file.", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File not found", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error initializing stream", Toast.LENGTH_LONG).show();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error closing file output stream", Toast.LENGTH_LONG).show();
            }
        }
    }
}