public class DocumentsContract_renameDocument {
    public void test_DocumentsContract_renameDocument() {
        Uri documentUri = null; 

        if (documentUri != null) {
            ContentResolver contentResolver = getContentResolver();
            DocumentFile documentFile = DocumentFile.fromSingleUri(this, documentUri);

            if (documentFile != null && documentFile.canWrite()) {
                try {
                    String newDocumentName = "newDocumentName";
                    documentUri = DocumentsContract.renameDocument(
                            contentResolver,
                            documentFile.getUri(),
                            newDocumentName);
                    
                    Toast.makeText(this, "Document renamed successfully", Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Document not found", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Document cannot be renamed", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Document URI is not valid", Toast.LENGTH_LONG).show();
        }
    }
}