public class File_length {
    public void test_File_length() {  
        // define a file path, you can modify this according to your requirement
        String filePath = getApplicationContext().getFilesDir().getPath() + "/test.txt";

        try {  
            // create a new file object  
            File file = new File(filePath);  

            // Check if the file exists and create a new one if it doesn't
            if (!file.exists()){
                file.createNewFile();
            }

            // Write some data to the file
            FileWriter writer = new FileWriter(file);
            writer.append("Test File for length method");
            writer.flush();
            writer.close();

            // get the length of the file 
            long lengthOfFile = file.length();  

            Log.d("TestFileLength", "Size of file in bytes: " + lengthOfFile); 
        } catch(IOException e) {  
            // print stacktrace in case of exception  
            e.printStackTrace();  
        }  
    }
}