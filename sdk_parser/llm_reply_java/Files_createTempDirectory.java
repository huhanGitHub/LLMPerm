public class Files_createTempDirectory {
    public void test_Files_createTempDirectory() {
        try { 
            // Import necessary classes
            Class.forName("java.nio.file.Files");
            Class.forName("java.nio.file.Path");
            Class.forName("java.nio.file.Paths");

            // The Files.createTempDirectory() method creates a new directory in the default temporary-file directory, using the given prefix to generate its name. 
            // Here, we are using 'TestTempDir' as our prefix
            java.nio.file.Path tempDir = java.nio.file.Files.createTempDirectory("TestTempDir");

            // Output the path of the temporary directory to standard output
            System.out.println("Temporary Directory Path: " + tempDir.toString());

        } catch(Exception e) {
            // Exception handling code
            e.printStackTrace();
        }
    }
}