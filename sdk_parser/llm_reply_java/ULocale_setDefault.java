public class ULocale_setDefault {
    public void test_ULocale_setDefault() {
        try {
            // Create a ULocale for Italian.
            ULocale italianLocale = new ULocale("it_IT");
                
            // Set this as the default ULocale.
            ULocale.setDefault(ULocale.Category.FORMAT, italianLocale);

            // Fetch the current default ULocale.
            ULocale currentLocale = ULocale.getDefault(ULocale.Category.FORMAT);

            // Print out the result.
            System.out.println("The default locale is now " + currentLocale.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}