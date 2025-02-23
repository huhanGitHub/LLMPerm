public class File_isHidden {
    
    /**
     * Test if a file is hidden.
     *
     * @param filePath Absolute path to the file
     * @return true if the file is hidden, false otherwise
     * @throws IOException If an I/O error occurs
     */
    private boolean test_File_isHidden(String filePath) throws IOException {
        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException("File " + filePath + " does not exist");
        }

        return file.isHidden();
    }
}