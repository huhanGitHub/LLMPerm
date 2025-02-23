public class Files_size {
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void test_Files_size() {
        // Set path to a file
        File file = new File(Environment.getExternalStorageDirectory(), "myFile.txt");

        try {
            // Convert the file to Path object
            Path path = Paths.get(file.getAbsolutePath());

            // Use Files.size() method to get size of the file
            long filesize = Files.size(path);

            // Print the file size
            System.out.println("Size of the file is: " + filesize + " bytes");
        } catch (IOException e) {
            // Handle exception
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}