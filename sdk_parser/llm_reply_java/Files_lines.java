public class Files_lines {
    public static void test_Files_lines() {
        Path filePath = Paths.get("path_to_your_file.txt");

        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}