public class Security_getProperty {
    public void test_Security_getProperty() {
        // Getting a security property with name "ssl.KeyManagerFactory.algorithm"
        String property = java.security.Security.getProperty("ssl.KeyManagerFactory.algorithm");

        // This will print the value of property "ssl.KeyManagerFactory.algorithm" to the console.
        // The default value is usually "SunX509".
        System.out.println("Value of property ssl.KeyManagerFactory.algorithm : " + property);
        
        // if you are inside an Android Activity class you can show this in a Toast:
        // Toast.makeText(this, "Value of property 'ssl.KeyManagerFactory.algorithm': " + property, Toast.LENGTH_LONG).show();
    }
}