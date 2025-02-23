public class DnsResolver_query {
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void test_DnsResolver_query(Context context){
        DnsResolver resolver = DnsResolver.getInstance();
        Network network = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetwork();
        byte[] query = DnsPacket.buildAQuery("www.example.com", (short) 0);

        try {
            CancellationSignal cancellationSignal = new CancellationSignal();
            resolver.query(network, query, cancellationSignal, new DnsResolver.Callback<byte[]>() {
                @Override
                public void onAnswer(@Nullable byte[] answer, int rcode) {
                    // onAnswer() gets called when a DNS answer is available.
                    // rcode is the DNS RCODE value or -1 if the answer is incomplete or malformed
                    // answer is byte representation of the full DNS name service record set
                }

                @Override
                public void onError(@NotNull DnsResolver.DnsException error) {
                    // onError() gets called when there was any error in getting answer.
                    // error is the type of error occurred while retrieving DNS details.
                }
            });
            // Use cancellationSignal if needed to cancel and stop onAnswer(), onError() from being called
            // cancellationSignal.cancel();

        }catch (ErrnoException errnoException){
            // Handle exception here
            errnoException.printStackTrace();
        }
    }
}