public class ProcessBuilder_start {
    private String test_ProcessBuilder_start() {
        try {
            // Create a stream to hold the output
            ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
            PrintStream outputStream = new PrintStream(outputByteArray);

            // Create a new instance of ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder("ls", "-l");
            processBuilder.redirectErrorStream(true);

            // start the process
            Process process = processBuilder.start();

            // Read the output from the command
            try (BufferedReader bufferedReader = 
                new BufferedReader(new InputStreamReader(process.getInputStream()))) 
            {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    outputStream.println(line);
                }
            }

            // Wait for the process to finish
            process.waitFor();

            // Return the output
            return outputByteArray.toString();

        } catch (IOException | InterruptedException e) {
            // Print the exception
            e.printStackTrace();

            // Return the exception message
            return "Error: " + e.getMessage();
        }
    }
}