public class SliceProvider_query {
    public void test_SliceProvider_query() {
        Uri uri = Uri.parse("content://com.example.slice/content_path");
        SliceProvider sliceProvider = new SliceProvider() {

            @Override
            public Slice onBindSlice(Uri sliceUri) {
                ListBuilder listBuilder = new ListBuilder(getContext(), sliceUri);
                listBuilder.addRow(new ListBuilder.RowBuilder()
                        .setTitle("Row title")
                        .setSubtitle("Row subtitle")
                        .setPrimaryAction(
                                new SliceAction(null, IconCompat.createWithResource(getContext(),
                                        R.drawable.ic_launcher), SliceAction.MODE_PERFORM,
                                        "Primary action")));
                return listBuilder.build();
            }

            @Override
            public Uri onMapIntentToUri(Intent intent) {
                return uri;
            }
        };

        SliceProvider.setSpecs(Set.of(SliceSpecs.LIST, SliceSpecs.BASIC));
        Slice slice = sliceProvider.onBindSlice(uri);

        // Do something with the slice
    }
}