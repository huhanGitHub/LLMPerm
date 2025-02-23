import java.lang.reflect.Proxy;
import android.util.Log;

public class Proxy_getProxyClass {

    public void test_Proxy_getProxyClass() {
        // Create ClassLoader instance
        ClassLoader classLoader = getClass().getClassLoader();

        // Array to hold the interfaces which the proxy class will implement
        Class[] interfaces = new Class[]{java.util.List.class};

        // Generate a proxy class via getProxyClass() method
        try {
            Class<?> proxyClass = Proxy.getProxyClass(classLoader, interfaces);

            // Printing the name of generated proxy class
            if (proxyClass != null)
                Log.i("ProxyTest", "Name of Proxy Class: " + proxyClass.getName());
        } catch (IllegalArgumentException e) {
            Log.e("ProxyTest", "Illegal argument provided to proxy class generator: " + e.getMessage());
        }
    }

}