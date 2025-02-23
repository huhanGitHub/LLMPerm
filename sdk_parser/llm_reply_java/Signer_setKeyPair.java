public class Signer_setKeyPair {
    public void test_Signer_setKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");
            keyPairGen.initialize(2048);
            KeyPair pair = keyPairGen.generateKeyPair();
            
            Signer mySigner = new Signer("DSA Signer") {
                public byte[] sign() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
                    Signature sign = Signature.getInstance("SHA256withDSA");
                    sign.initSign(getPrivateKey());
                    String data = "This is a test data";
                    sign.update(data.getBytes());
                    byte[] signature = sign.sign();
                    return signature;
                }
            };
            
            mySigner.setKeyPair(pair);
            
            byte[] signature = mySigner.sign();
            
            System.out.println("Signature generated successfully");
            
            // To verify the signature:
            try {
                Signature signatureForVerify = Signature.getInstance("SHA256withDSA");
                signatureForVerify.initVerify(mySigner.getPublicKey());
                signatureForVerify.update("This is a test data".getBytes());
                
                boolean isVerified = signatureForVerify.verify(signature);
                if(isVerified) {
                    System.out.println("Saved signature matched");
                } else {
                    System.out.println("Saved signature did not match");
                }
            } catch (Exception e) {
                System.err.println("Exception while verifying: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}