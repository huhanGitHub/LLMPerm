import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;

public class SSLSocketFactory_createSocket {
    public void test_SSLSocketFactory_createSocket() {
        try {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("google.com", 443);

            System.out.println("Connected to google.com");

        } catch (IOException e) {
            System.err.println("Could not create socket to google.com:443");
            e.printStackTrace();
        }
    }
}