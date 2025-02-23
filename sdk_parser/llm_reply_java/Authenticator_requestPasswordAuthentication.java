public class Authenticator_requestPasswordAuthentication {

    public void test_Authenticator_requestPasswordAuthentication() {
        // Setting the default Authenticator
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // Provide your own account credentials
                String username = "yourUsername";
                String password = "yourPassword";

                // Prompting the user for a username and a password
                if (getRequestingScheme().equals("digest")) {
                    // Return the information (a data holder that is used by Authenticator)
                    return new PasswordAuthentication(username, password.toCharArray());
                } else {
                    return null;
                }
            }
        });

        try {
            // Pass your URL
            URL url = new URL("http://www.example.com");
            URLConnection connection = url.openConnection();
            // Trigger the Authenticator
            connection.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}