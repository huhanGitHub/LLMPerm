public class File_setExecutable {
    public void test_File_setExecutable() {
        // Create a new file
        File file = new File("testFile.txt");
        try {
            if (file.createNewFile()) {
                System.out.println("File created.");
            } else {
                System.out.println("File already exists.");
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        // Set the file to be executable for the owner
        boolean isSet = file.setExecutable(true, true);
        if (isSet) {
            System.out.println("File is set to be executable for the owner.");
        } else {
            System.out.println("Failed to set the file to be executable.");
        }

        // Check if the file is executable
        if (file.canExecute()) {
            System.out.println("File is executable.");
        } else {
            System.out.println("File is not executable.");
        }

        // Set the file to be non-executable
        isSet = file.setExecutable(false);
        if (isSet) {
            System.out.println("File is set to be non-executable.");
        } else {
            System.out.println("Failed to set the file to be non-executable.");
        }

        // Check if the file is executable
        if (!file.canExecute()) {
            System.out.println("File is not executable.");
        } else {
            System.out.println("File is still executable.");
        }

        // Finally, delete the file
        if (file.delete()) {
            System.out.println("File deleted.");
        } else {
            System.out.println("Failed to delete the file.");
        }
    }
}