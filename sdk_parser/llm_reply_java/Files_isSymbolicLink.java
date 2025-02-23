public class Files_isSymbolicLink {
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void test_Files_isSymbolicLink() {
        // Please replace with a valid path in your device
        Path path = Paths.get("/storage/emulated/0/Download/some_link");
        
        // Checking if the path exists, to avoid NoSuchFileException
        if (Files.exists(path)) {
            try {
                boolean isSymbolicLink = Files.isSymbolicLink(path);
                
                if (isSymbolicLink) {
                    // Handle if file is a symbolic link
                    // For simplicity, we're just showing a Toast message
                    Toast.makeText(this, "This path is a symbolic link!", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle if file isn't a symbolic link
                    // For simplicity, we're just showing a Toast message
                    Toast.makeText(this, "This path is NOT a symbolic link!", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException | UnsupportedOperationException e) {
                // Handle exception here
                e.printStackTrace();
            }
        } else {
            // Handle if file doesn't exist
            // For simplicity, we're just showing a Toast message
            Toast.makeText(this, "File does not exist!", Toast.LENGTH_SHORT).show();
        }
    }
}