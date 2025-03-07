public class ProxySelector_setDefault {
    public void test_ProxySelector_setDefault() {
        ProxySelector.setDefault(new ProxySelector() {
            @Override
            public List<Proxy> select(URI uri) {
                // This ProxySelector uses NO_PROXY for all URIs
                List<Proxy> list = new ArrayList<>();
                list.add(Proxy.NO_PROXY);
                return list;
            }

            @Override
            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                // Just output the error for the sake of this example
                System.err.println("Could not connect to " + uri + " " + sa);
                ioe.printStackTrace();
            }
        });

        // Test if our custom ProxySelector is now the default one
        ProxySelector ps = ProxySelector.getDefault();
        List<Proxy> proxies = ps.select(URI.create("http://example.com"));
        for (Proxy proxy : proxies) {
            if (proxy.equals(Proxy.NO_PROXY)) {
                System.out.println("Our ProxySelector is the default one.");
                return;
            }
        }

        throw new AssertionError("Our ProxySelector is not the default one.");
    }
}