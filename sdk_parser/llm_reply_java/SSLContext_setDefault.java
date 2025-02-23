import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;
import java.security.SecureRandom;

public class SSLContext_setDefault {
    public void test_SSLContext_setDefault() {
        SSLContext sslContext;
        try {
            // Create SSLContext
            sslContext = SSLContext.getInstance("TLS");

            // Initialize SSLContext
            sslContext.init(null, null, new SecureRandom());

            // Set it as default
            SSLContext.setDefault(sslContext);

            // Test that the default is correctly set
            SSLSocketFactory defaultSSLSocketFactory = HttpsURLConnection.getDefaultSSLSocketFactory();
                        
            if (defaultSSLSocketFactory.equals(sslContext.getSocketFactory())) {
                System.out.println("Successfully set default SSLContext");
            } else {
                System.out.println("Failed to set default SSLContext");
            }
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }
}