import android.app.backup.BackupDataInputStream;
import android.app.backup.FileBackupHelper;
import android.content.Context;

import java.io.File;
import java.io.IOException;

public class FileBackupHelper_restoreEntity {
    private FileBackupHelper fileBackupHelper;
    private static final String FILE_NAME = "myFile";

    public FileBackupHelper_restoreEntity(Context context) {
        String[] files = { FILE_NAME };
        fileBackupHelper = new FileBackupHelper(context, files);
    }

    private void test_FileBackupHelper_restoreEntity() {
        try {
            File newFile = new File(getFilesDir(), FILE_NAME);

            if(newFile.exists()) {
                //Delete if file already exists to simulate restore
                newFile.delete();
            }

            // Create a mock BackupDataInputStream
            BackupDataInputStream dataInput = new MockBackupDataInputStream(FILE_NAME, new byte[1024]);
            // Restores the given entity's data from the current backup data set into the given file
            fileBackupHelper.restoreEntity(dataInput);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mock class to simulate the behavior of BackupDataInputStream.
     */
    private class MockBackupDataInputStream extends BackupDataInputStream {
        private byte[] mMockData;

        MockBackupDataInputStream(String key, byte[] mockData) {
            super(new BackupDataInput(null));
            mMockData = mockData;
        }

        @Override
        public String getKey() {
            return FILE_NAME;
        }

        @Override
        public int read(byte[] buffer, int offset, int length) throws IOException {
            System.arraycopy(mMockData, offset, buffer, 0, mMockData.length-offset);
            return mMockData.length-offset;
        }
    }
}