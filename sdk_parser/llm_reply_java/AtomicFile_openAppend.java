public class AtomicFile_openAppend {

    private final String TAG = "PermissionTestActivity";

    @Test
    public void test_AtomicFile_openAppend() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "test_file.txt");

            // Check basic file properties & permissions
            assertTrue("File does not exist!", file.exists());
            assertTrue("Cannot read file!", file.canRead());
            assertTrue("Cannot write file!", file.canWrite());

            AtomicFile atomicFile = new AtomicFile(file);

            // Try to open the file in append mode & check for IOException
            try (FileOutputStream fileOutputStream = atomicFile.openAppend()) {
                assertNotNull(fileOutputStream);

                String testString = "This is a test string.";
                fileOutputStream.write(testString.getBytes());

                fileOutputStream.flush();
            } catch (FileNotFoundException e) {
                Log.e(TAG, "Failed to find the file: " + file.getAbsolutePath(), e);
                fail("Failed to find the file: " + file.getAbsolutePath());
            }
            catch (IOException e) {
                Log.e(TAG, "Failed to open file in append mode: " + file.getAbsolutePath(), e);
                fail("Failed to open file in append mode: " + file.getAbsolutePath());
            }

            // Don’t forget to close the stream
            finally {
                // Commit all changes to the atomic file
                atomicFile.finishWrite(fileOutputStream);
            }

        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Invalid file path", e);
            fail("Invalid file path");
        }
    }
}