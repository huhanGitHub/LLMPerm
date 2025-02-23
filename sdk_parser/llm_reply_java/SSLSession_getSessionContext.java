import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class SSLSession_getSessionContext {

    public void test_SSLSession_getSessionContext() {
        // Generating SSL Context
        SSLContext sslContext = generateSSLContext();

        // Creating a dummy SSLSession to get the SessionContext from it
        SSLSession sslSession = sslContext.getServerSessionContext().getSession("dummy session".getBytes());

        // Getting session context
        SSLSessionContext sslSessionContext = sslSession.getSessionContext();

        if (sslSessionContext != null) {
            System.out.println("SSL session context is successfully retrieved.");
        } else {
            System.out.println("Failed to retrieve the SSL session context.");
        }
    }

    private SSLContext generateSSLContext() {
        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, null);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("Failed to instanciate sslContext", e);
        }
        return sslContext;
    }
}