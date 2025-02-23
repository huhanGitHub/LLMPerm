public class SearchRecentSuggestionsProvider_query {
    public void test_SearchRecentSuggestionsProvider_query() {
        // Initialize with the unique authority string 
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, 
            SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
        
        // Insert a suggestion into the database
        suggestions.saveRecentQuery("Android Guide", null);

        // Create a cursor and retrieve all the data
        Cursor cursor = suggestions.getQueryCursor("Android Guide", null);
        
        if (cursor.moveToFirst()){
            do{
                String query = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
                Log.d("SUGGESTION", query);
            } while (cursor.moveToNext());
        }
        
        // Do not forget to close the cursor
        cursor.close();
    }
}