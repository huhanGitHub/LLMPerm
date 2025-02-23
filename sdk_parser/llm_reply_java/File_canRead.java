public class File_canRead {
    public boolean test_File_canRead(String filePath) {
        // create new file object
        File file = new File(filePath);

        // tests whether the application can read the file  
        boolean canRead = file.canRead();

        if(canRead) {
            System.out.println("The file is accessible for read operation");
        } else {
            System.out.println("The file is not accessible for read operation");
        }
        
        return canRead;
    }
}