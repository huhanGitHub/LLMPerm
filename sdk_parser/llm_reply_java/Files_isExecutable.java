import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Files_isExecutable {
    public void test_Files_isExecutable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // File path - Modify based on the file you want to check
                String filePath = "/path/to/your/file";

                try {
                    // Check if file is executable
                    if (Files.exists(Paths.get(filePath))) {
                        boolean isExecutable = Files.isExecutable(Paths.get(filePath));
                        if (isExecutable) {
                            System.out.println("The file is executable.");
                        } else {
                            System.out.println("The file is not executable.");
                        }
                    } else {
                        System.out.println("The file does not exist.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}