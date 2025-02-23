import android.net.Uri;
import android.util.Log;
import java.util.List;

public class Uri_getCanonicalUri {
    public void test_Uri_getCanonicalUri() {
        // Construct a Uri
        Uri uri = Uri.parse("https://www.example.com/path1/path2?query=param#fragment");

        // Get the different components of the Uri
        String scheme = uri.getScheme(); // "https"
        String host = uri.getHost(); // "www.example.com"
        List<String> pathSegments = uri.getPathSegments(); // ["path1", "path2"]
        String query = uri.getQuery(); // "query=param"
        String fragment = uri.getFragment(); // "fragment"

        // Reconstruct the Uri
        Uri.Builder builder = new Uri.Builder()
                .scheme(scheme)
                .authority(host)
                .fragment(fragment);
        for (String pathSegment : pathSegments) {
            builder.appendPath(pathSegment);
        }
        Uri canonicalUri = builder.build();

        // Print the canonicalUri
        Log.d("test_Uri_getCanonicalUri", canonicalUri.toString());
    }
}