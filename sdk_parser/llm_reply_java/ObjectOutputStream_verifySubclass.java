public class ObjectOutputStream_verifySubclass {
    public static void test_ObjectOutputStream_verifySubclass() {
        ObjectOutputStream objectOutputStream = null;
        try {
            // create a new file with an ObjectOutputStream
            objectOutputStream = new ObjectOutputStream(new FileOutputStream("testFile.bin"));
            
            // write something in the file
            objectOutputStream.writeUTF("Hello World");

            // flush the stream to insure all of the information was written to 'testfile.bin'
            objectOutputStream.flush();

            // close output stream when we're done with it
            objectOutputStream.close();
        } catch(IOException ex) {
            ex.printStackTrace();
        } finally {
            if(objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}