public class DocumentsContract_deleteDocument {
    public boolean test_DocumentsContract_deleteDocument(ContentResolver contentResolver, Uri documentUri) {
        boolean isSuccess = false;
        
        try {
            DocumentsContract.deleteDocument(contentResolver, documentUri);
            isSuccess = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}