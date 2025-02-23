public class UserPrincipalLookupService_lookupPrincipalByGroupName {
    public void test_UserPrincipalLookupService_lookupPrincipalByGroupName() {
        UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
        try {
            UserPrincipal user = lookupService.lookupPrincipalByGroupName("admin");
            // Use the UserPrincipal object in your code...
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}