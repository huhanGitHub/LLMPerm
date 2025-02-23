public class File_list {
    /**
    * Demonstrates the usage of the list method of the java.io.File class
    */
    public void test_File_list(String directoryPath) {
        // Creating a File object for directory
        File directory = new File(directoryPath);

        // Get all the list of files in string array
        String[] files = directory.list();

        if (files != null) {
            for (String file : files) {
                System.out.println("File: " + file);
            }
        } else {
            System.out.println("The specified directory does not exist or an I/O error occurred.");
        }
    }
}