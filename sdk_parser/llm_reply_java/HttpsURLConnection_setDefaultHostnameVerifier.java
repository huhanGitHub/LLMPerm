import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class HttpsURLConnection_setDefaultHostnameVerifier {
    public void test_HttpsURLConnection_setDefaultHostnameVerifier() {
        // create a custom HostnameVerifier
        HostnameVerifier myHostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                // it's a good practice to check host validity
                if (hostname.equals("www.google.com")) {
                    return true;
                }
                return false;
            }
        };

        // set the default Hostname Verifier
        HttpsURLConnection.setDefaultHostnameVerifier(myHostnameVerifier);

        // Now when you initiate an https connection, the provided HostnameVerifier will be used to check the hostname validity.
    }
}