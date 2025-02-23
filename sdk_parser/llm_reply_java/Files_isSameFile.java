public class Files_isSameFile {
    public void test_Files_isSameFile() {
        // Defining two paths
        Path path1 = Paths.get("/tmp/file1.txt");
        Path path2 = Paths.get("/tmp/file1.txt");

        try {
            // Using Files.isSameFile
            boolean isSameFile = Files.isSameFile(path1, path2);

            // Printing if the two paths map to same file
            System.out.println(isSameFile);
        } catch (IOException e) {
            // Handling exception
            e.printStackTrace();
        }
    }
}