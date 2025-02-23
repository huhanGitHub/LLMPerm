public class Path_toAbsolutePath {
    public void test_Path_toAbsolutePath() {
        try {
            // Define a relative path
            Path relativePath = Paths.get("test.txt");

            // Convert the relative path to an absolute path
            Path absolutePath = relativePath.toAbsolutePath();

            // Print both the relative and absolute paths to console
            System.out.println("Relative path: " + relativePath.toString());
            System.out.println("Absolute path: " + absolutePath.toString());

        } catch (Exception e) {
            // print stack trace in case of an exception
            e.printStackTrace();
        }
    }
}