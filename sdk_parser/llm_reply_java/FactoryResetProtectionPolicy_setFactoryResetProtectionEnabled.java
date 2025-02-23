public class FactoryResetProtectionPolicy_setFactoryResetProtectionEnabled {
    private void test_FactoryResetProtectionPolicy_setFactoryResetProtectionEnabled() {
        if (mDevicePolicyManager.isAdminActive(mAdminComponentName)) {
            FactoryResetProtectionPolicy policy = mDevicePolicyManager.getFactoryResetProtectionPolicy(mAdminComponentName);
            FactoryResetProtectionPolicy.Builder builder = new FactoryResetProtectionPolicy.Builder(policy);
            builder.setFactoryResetProtectionEnabled(true);
            mDevicePolicyManager.setFactoryResetProtectionPolicy(mAdminComponentName, builder.build());
            Toast.makeText(this, "FactoryResetProtectionPolicy set to enabled.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not a device admin", Toast.LENGTH_SHORT).show();
        }
    }
}