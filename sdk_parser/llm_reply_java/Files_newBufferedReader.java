public class Files_newBufferedReader {
    public void test_Files_newBufferedReader() {
        // Set your own file path
        String filePathStr = "path-to-your-file";
        
        Path filePath = Paths.get(filePathStr);

        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;

            // Read the content of the file line by line
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            // Exception handling here (print stack trace, log...)
            e.printStackTrace();
        }
    }
}