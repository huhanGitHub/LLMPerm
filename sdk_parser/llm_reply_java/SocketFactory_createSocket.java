import java.io.IOException;
import java.net.Socket;
import javax.net.SocketFactory;

public class SocketFactory_createSocket {
    public void test_SocketFactory_createSocket() {
        SocketFactory socketFactory = SocketFactory.getDefault();
        Socket socket = null;
        try {
            socket = socketFactory.createSocket("192.168.1.2", 8080);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}