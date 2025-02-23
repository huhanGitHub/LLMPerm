public class CharsetProvider_checkPermission {

    private void test_CharsetProvider_checkPermission() {
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                // deny any permission that isn't "charsetProvider"
                if (!perm.getName().startsWith("charsetProvider")) {
                    super.checkPermission(perm);
                }
            }
        });

        CharsetProvider provider = new CharsetProvider() {
            @Override
            public Iterator<Charset> charsets() {
                return null;
            }

            @Override
            public Charset charsetForName(String charsetName) {
                return null;
            }
        };

        try {
            Charset charset = provider.charsetForName("UTF-8");
            System.out.println("Charset: " + charset);
        } catch (SecurityException ex) {
            System.err.println("Permission was not granted for the charset provider");
        }
    }
}