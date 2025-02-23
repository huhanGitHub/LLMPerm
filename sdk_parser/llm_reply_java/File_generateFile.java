public class File_generateFile {
    public void test_File_generateFile(Context context) {
        try {
            // Create a File object for a new file in the application's files directory
            File file = new File(context.getFilesDir(), "exampleFile.txt");

            // If file does not exist, a new file is created
            if (!file.exists()) {
                file.createNewFile();
            }

            // String that will be written into the file
            String content = "This is an example file generated using java.io.File";

            // FileWriter is used to write the string into the file
            FileWriter fileWriter = new FileWriter(file);

            // Writing the string to the file
            fileWriter.write(content);

            // Closing the FileWriter
            fileWriter.close();

            Toast.makeText(context, "File created successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error in file creation", Toast.LENGTH_SHORT).show();
        }
    }
}