import java.security.Identity;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLContext;
import java.security.Signer;

public class Identity_addCertificate {
    public void test_Identity_addCertificate() {
        try {
            // Create a new identity
            Signer signer = null;
            Identity myIdentity = new Identity("My Identity", signer);

            // Generate a dummy certificate for testing purpose
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };

            // Add certificate to our identity
            myIdentity.addCertificate((Certificate) trustAllCerts[0]);

            // Now you can view the certificate using the certificates() method
            System.out.println(myIdentity.certificates());

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}