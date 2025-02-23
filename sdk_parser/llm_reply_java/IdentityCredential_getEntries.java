public class IdentityCredential_getEntries {

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void test_IdentityCredential_getEntries() {
        IdentityCredentialStore store = IdentityCredentialStore.getInstance(this);
        String namespace = "androidx.test";
        try {
            WritableIdentityCredential credential = store.createCredential(namespace, "test");
            PersonalizationData personalizationData = PersonalizationData.Builder
                    .newBuilder()
                    .addEntry(namespace, "testEntry", new EntryValue("testValue"))
                    .build();

            credential.personalize(personalizationData);

            byte[] challenge = new byte[16];
            new SecureRandom().nextBytes(challenge);

            IdentityCredential ic = credential.getCredential();

            ResultData rd = ic.getEntries(challenge, new HashSet<String>(Arrays.asList("testEntry")));
            Collection<ResultNamespace> namespaces = rd.getEntries();
            for (ResultNamespace ns : namespaces ) {
                Collection<Entry> entries = ns.getEntries();
                for (Entry e : entries) {
                    String name = e.getName();
                    String value = new String(e.getValue(), Charset.forName("UTF-8"));
                    Toast.makeText(this, "Entry Name: " + name + ", Entry Value: " + value, Toast.LENGTH_LONG).show(); 
                }
            }
        } catch (IdentityCredentialException | IOException e) {
            e.printStackTrace();
        }
    }
}