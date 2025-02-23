public class Files_readAllLines {
    public void test_Files_readAllLines() {
        // Specify the file path here. It should exist in the Android file system.
        String filePath = "/path/to/your/file.txt"; 

        try {
            List<String> allLines = Files.readAllLines(Paths.get(filePath));
            for (String line : allLines) { 
                System.out.println(line); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}