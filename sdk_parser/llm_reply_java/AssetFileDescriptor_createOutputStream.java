public class AssetFileDescriptor_createOutputStream {
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void test_AssetFileDescriptor_createOutputStream() {
        try {
            // Mostly applicable to Android Q (API 29) and above
            String relativePath = Environment.DIRECTORY_DOWNLOADS + File.separator + "TestDir";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Files.FileColumns.RELATIVE_PATH, relativePath);
            values.put(MediaStore.Files.FileColumns.IS_PENDING, 1);

            ContentResolver resolver = getApplicationContext().getContentResolver();
            Uri collection = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            Uri item = resolver.insert(collection, values);

            if (item != null){
                try (OutputStream out = resolver.openOutputStream(item)) {
                    if (out != null) {
                        byte[] data = {1, 2, 3, 4, 5, 6, 7};
                        out.write(data);
                        values.clear();
                        values.put(MediaStore.Files.FileColumns.IS_PENDING, 0);
                        resolver.update(item, values, null, null);
                    }
                }catch (IOException e) {
                    resolver.delete(item, null, null);
                    e.printStackTrace();
                }
            }

            AssetFileDescriptor fileDescriptor = getApplicationContext().getContentResolver().openAssetFileDescriptor(item, "w");
            if (fileDescriptor != null) {
                // write into this file descriptor
                try (FileOutputStream fileOutputStream = fileDescriptor.createOutputStream()) {
                    // Check if the stream is not null
                    if (fileOutputStream != null) {
                        // Write something into file
                        fileOutputStream.write("Test write to file".getBytes());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Log.e("Permission error", "write failed", e);
        }
    }
}