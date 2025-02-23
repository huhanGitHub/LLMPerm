public class JapaneseEra_writeExternal {
    public void test_JapaneseEra_writeExternal() {
        try {
            // Create a JapaneseEra instance
            JapaneseEra era = JapaneseEra.HEISEI;

            // Create a ByteArrayOutputStream which is resizable to write to byte arrays
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            // Create an ObjectOutputStream to write our JapaneseEra into our ByteArrayOutputStream
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            // Write the JapaneseEra to the ObjectOutputStream
            era.writeExternal(oos);

            // Flush the ObjectOutputStream to ensure all data is written to the ByteArrayOutputStream
            oos.flush();

            // Convert the ByteArrayOutputStream data into a byte array
            byte[] byteArray = bos.toByteArray();

            // Close the ObjectOutputStream
            oos.close();

            // Log the byte array
            Log.d("JapaneseEraByteArray", Arrays.toString(byteArray));
        } catch (IOException e) {
            // Log the exception
            Log.e("JapaneseEraByteArrayError", e.getMessage());
        }
    }
}