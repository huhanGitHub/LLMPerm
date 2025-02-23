import java.net.ProxySelector;

public class ProxySelector_getDefault {
    public void test_ProxySelector_getDefault() {
        // Get the default ProxySelector
        ProxySelector defaultProxySelector = ProxySelector.getDefault();

        // If no default is set
        if (defaultProxySelector == null) {
            System.out.println("No default ProxySelector is set");
        } else {
            System.out.println("Default ProxySelector is: " + defaultProxySelector);
        }
    }
}