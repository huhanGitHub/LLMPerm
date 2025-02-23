public class DocumentsContract_getRootId {
    public void test_DocumentsContract_getRootId() {
        ContentResolver resolver = getContentResolver();
        Uri documentsUri = DocumentsContract.buildDocumentUri("com.example.provider", "/path/document");
        String rootId = null;

        Cursor cursor = null;
        try {
            cursor = resolver.query(documentsUri, new String[]{DocumentsContract.Document.COLUMN_DOCUMENT_ID}, null, null, null);
            while (cursor != null && cursor.moveToNext()) {
                rootId = DocumentsContract.getRootId(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        if (rootId != null) {
            Log.d("Test", "Root ID: " + rootId);
        } else {
            Log.d("Test", "Root ID not found");
        }
    }
}