import java.io.File;
import java.io.IOException;

public class File_setReadable {
    public void test_File_setReadable() {
        //Create a new file
        File file = new File("test.txt");
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println("The file has been created");
                } else {
                    System.out.println("The file already exists");
                }
            }
            //First parameter is to make the file readable, second one is whether only the owner can read it
            //Setting it to true means only the owner can read, setting it to false means everyone can read it
            if (file.setReadable(true, false)) {
                System.out.println("The file is now readable for everyone");
            } else {
                System.out.println("The operation failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}