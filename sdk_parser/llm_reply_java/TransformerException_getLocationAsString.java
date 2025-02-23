public class TransformerException_getLocationAsString {
    public void test_TransformerException_getLocationAsString() {
        try {
            // Create a new transformer factory
            TransformerFactory factory = TransformerFactory.newInstance();

            // Create a transformer for the stylesheet.
            Transformer transformer = factory.newTransformer();

            // create Source instance from XML file using StreamSource class
            Source src = new StreamSource(new File("input.xml"));

            // create Result instance to output the transformed content
            Result res = new StreamResult(new File("output.xml"));

            // Perform the transformation. This will throw a TransformerException if there's any issue
            transformer.transform(src, res);

        } catch (TransformerException e) {
            // Get the location of error in the XML file which caused the exception
            String errorLocation = e.getLocationAsString();

            // Print the error location
            System.out.println(errorLocation);
        }
    }
}