public class BackupAgent_doFullBackup {
    public void test_BackupAgent_doFullBackup() {
        try {
            // Assuming we have sampleFile for testing purpose
            File sampleFile = new File("<sample_file_path>"); 

            // Creating an instance of File descriptor from the file
            ParcelFileDescriptor parcelFileDescriptor =
                    ParcelFileDescriptor.open(sampleFile, ParcelFileDescriptor.MODE_READ_ONLY);

            // Creating an instance of FullBackup
            FullBackup fullBackup = new FullBackup();

            // Creating an instance of Backup Data Output
            BackupDataOutput dataOutput = new BackupDataOutput(parcelFileDescriptor.getFileDescriptor());

            try {
                // Perform the backup
                fullBackup.backupToTar("<package_name>", null, null, sampleFile.getParent(),
                        sampleFile.getName(), dataOutput);

                Log.d("TEST", "Backup was successful.");
            } catch (IOException e) {
                Log.e("TEST", "Failed to perform the backup", e);
            }
        } catch (FileNotFoundException e) {
            Log.e("TEST", "Cannot find the file to backup", e);
        }
    }
}