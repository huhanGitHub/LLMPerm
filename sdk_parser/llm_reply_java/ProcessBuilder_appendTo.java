public class ProcessBuilder_appendTo {
    public void test_ProcessBuilder_appendTo() {
        // Defined the command to run
        List<String> command = new ArrayList<>();
        command.add("ls"); // Change the command as per your needs
        command.add("-l");

        // Define the output file
        File outputFile = new File("output.txt");

        try {
            // Create ProcessBuilder
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(new File("/")); // Set the working directory

            // Redirect output and error stream to a file
            pb.redirectOutput(outputFile);
            pb.redirectErrorStream(true);

            // Run the command
            Process process = pb.start();
            process.waitFor(); // Wait for the process to finish

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
}