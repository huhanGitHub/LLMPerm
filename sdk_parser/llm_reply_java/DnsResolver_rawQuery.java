public class DnsResolver_rawQuery {
    private void test_DnsResolver_rawQuery() {
        String domainName = "example.com";

        // Assuming DnsResolver.rawQuery method accepts domain name and callback
        DnsResolver instance = DnsResolver.getInstance();
        try {
            Method method = DnsResolver.class.getDeclaredMethod("rawQuery", String.class, ResponseCallback.class);
            method.setAccessible(true);
            method.invoke(instance, domainName, new ResponseCallback() {
                @Override
                public void onAnswer(ByteBuffer answer) {
                    // Handle the ByteBuffer answer here
                }
            });
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public interface ResponseCallback {
        void onAnswer(ByteBuffer answer);
    }
}