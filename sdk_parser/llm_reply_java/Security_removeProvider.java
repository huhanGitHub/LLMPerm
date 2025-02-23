public class Security_removeProvider {
    public void test_Security_removeProvider() {
        //The name of the security provider to remove
        String providerName = "BC"; //For example, Bouncy Castle provider

        if (Security.getProvider(providerName) != null) {
            //The removeProvider method returns void
            Security.removeProvider(providerName);
            System.out.println("Security provider removed.");
        } else {
            System.out.println("Provider not found. Unable to remove.");
        }
    }
}