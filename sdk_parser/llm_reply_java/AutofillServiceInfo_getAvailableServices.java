public class AutofillServiceInfo_getAvailableServices {

    public void test_AutofillServiceInfo_getAvailableServices(Context context) {

        // Permission check for WRITE_SECURE_SETTINGS.
        if (context.checkCallingOrSelfPermission(Manifest.permission.WRITE_SECURE_SETTINGS) 
            == PackageManager.PERMISSION_GRANTED) {
             // Permission is given, continue.

        } else {
            // If permission is not granted, display message.
            Toast.makeText(context, "WRITE_SECURE_SETTINGS permission is not granted", 
                           Toast.LENGTH_SHORT).show();
            return;
        }

        final AutofillManager autofillManager = context.getSystemService(AutofillManager.class);

        if (autofillManager != null && autofillManager.isEnabled()) {
            // If Autofill service is enabled

            // Get the list of available Autofill services
            List<AutofillServiceInfo> servicesList = autofillManager.getEnabledAutofillServices();

            // Print a message for each Autofill service available (enabled).
            for (AutofillServiceInfo serviceInfo : servicesList) {
                System.out.println("Service: " + serviceInfo.getServiceInfo().name +
                                   ", package: " + serviceInfo.getServiceInfo().packageName);
            }
        } else {
            // Autofill service is not enabled or autofillManager is null.
            System.out.println("Autofill service is not enabled.");
        }
    }
}