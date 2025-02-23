public class File_setWritable {
    void test_File_setWritable() {
        //creating a new file
        File file = new File("/path/to/your/file.txt");
        try {
            if (file.createNewFile()) {
                // set the file to be writable
                boolean isWritable = file.setWritable(true);
                if (isWritable) {
                    System.out.println("File is set to be writable!");
                } else {
                    System.out.println("Failed to set the file as writable!");
                }
            } else {
                System.out.println("File already exists!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}