public class SearchRecentSuggestions_saveRecentQueryBlocking {
    public void test_SearchRecentSuggestions_saveRecentQueryBlocking() {
        // Authority of your SearchRecentSuggestionsProvider.
        String AUTHORITY = "<Your_SearchRecentSuggestionsProvider_Authority>";
        // Mode for your SearchRecentSuggestions.
        int MODE = SearchRecentSuggestions.QUERY_HISTORY_DATABASE;

        // Instance of the SearchRecentSuggestions
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, AUTHORITY, MODE);

        // Your Search Query that we are going to save to Recent Suggestions.
        String query = "Your Search Query";

        // Save your Query to the recent searches.
        suggestions.saveRecentQuery(query, null);
    }
}