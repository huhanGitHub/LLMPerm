public class PasswordAuthentication_getPassword {
    public void test_PasswordAuthentication_getPassword() {
        // Create a PasswordAuthentication object
        PasswordAuthentication passwordAuthentication = new PasswordAuthentication("username", "password".toCharArray());

        // Use getPassword method
        char[] password = passwordAuthentication.getPassword();

        // Convert char array to string
        String passwordStr = new String(password);
        
        // Print password to console
        System.out.println("Password: " + passwordStr);

        // Clear the password
        for (int i = 0; i < password.length; i++) {
            password[i] = 0;
        }
    }
}