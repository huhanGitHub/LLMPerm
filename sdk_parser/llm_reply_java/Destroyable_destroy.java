public class Destroyable_destroy {
    private void test_Destroyable_destroy() {
        SensitiveData sensitiveData = new SensitiveData("Secret data");
        try {
            sensitiveData.destroy();
            // Log whether sensitive information is destroyed successfully
            Log.i("MainActivity", "SensitiveData object is destroyed: " + sensitiveData.isDestroyed());
        } catch (DestroyFailedException e) {
            e.printStackTrace();
        }
    }
}