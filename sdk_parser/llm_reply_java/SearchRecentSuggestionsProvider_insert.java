public class SearchRecentSuggestionsProvider_insert {
    public void test_SearchRecentSuggestionsProvider_insert() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://searchRoute");
        ContentValues contentValues = new ContentValues();
        contentValues.put(SearchManager.QUERY, "test");
        contentValues.put(SearchManager.SUGGEST_COLUMN_TEXT_1, "test1");
        contentResolver.insert(uri, contentValues);
        Cursor cursor = contentResolver.query(uri, null, null, null, SearchManager.SUGGEST_COLUMN_TEXT_1 + " ASC");
        
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String columnValue = cursor.getString(cursor.getColumnIndex(SearchManager.QUERY));
                assertTrue(columnValue.equals("test"));
            }
            cursor.close();
        }
    }
}