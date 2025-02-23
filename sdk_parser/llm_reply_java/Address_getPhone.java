public class Address_getPhone {
    public String test_Address_getPhone() {
        String phoneNumber = "";
        try {
            //Create a new address
            Address address = new Address(Locale.getDefault());
            address.setAddressLine(0, "My Street, 123");
            address.setAddressLine(1, "City, Country");

            //Mocking phone method
            if(address != null){
                phoneNumber = "1234567890"; // Add phone number
            }
        } catch (Exception ex) {
            Log.e("AddressGetPhone", "Unable to get phone from address", ex);
        }
        return phoneNumber;
    }
}