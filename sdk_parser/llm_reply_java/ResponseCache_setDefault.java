public class ResponseCache_setDefault {
    public void test_ResponseCache_setDefault() {
        ResponseCache.setDefault(new ResponseCache() {
            @Override
            public CacheResponse get(URI uri, String requestMethod, Map<String, List<String>> requestHeaders) throws IOException {
                // In real application, you may want to fetch a cached response from storage.
                // Return a CacheResponse if the response is available in cache, else return null
                return null;
            }

            @Override
            public CacheRequest put(URI uri, URLConnection urlConnection) throws IOException {
                // In real application, you may want to store some response to cache.
                // Return a CacheRequest that will store the response in cache or return null.
                return null;
            }
        });
    }
}