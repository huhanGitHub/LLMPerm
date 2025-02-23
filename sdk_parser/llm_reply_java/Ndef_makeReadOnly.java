public class Ndef_makeReadOnly {

    public void test_Ndef_makeReadOnly() {
        try {
            Tag tag = getTag();
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();

                if (ndef.isWritable()) {
                    ndef.makeReadOnly();

                    if (ndef.isWritable()) {
                        Toast.makeText(this, "Failed to make tag read-only", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Tag is now read-only", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "Tag is already read-only", Toast.LENGTH_SHORT).show();
                }

                ndef.close();

            } else {
                Toast.makeText(this, "Ndef is null", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("Test", "Error: " + e.getMessage());
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Tag getTag() {
        // This method needs to be implemented to provide a real Tag object
        return null;
    }
}