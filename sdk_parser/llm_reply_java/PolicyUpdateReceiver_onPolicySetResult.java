public class PolicyUpdateReceiver_onPolicySetResult {
    public void test_PolicyUpdateReceiver_onPolicySetResult() {
        PolicyUpdateReceiver policyUpdateReceiver = new PolicyUpdateReceiver();
        Intent intent = new Intent();

        // Add extra information to the intent if necessary, the given string is an example and should be replaced by relevant data
        intent.putExtra("POLICY_RESULT", "Policy Set");

        // Since onPolicySetResult is a callback method and its access is not suggested, 
        // we can test receiver's onReceive method, assuming it in turns calls onPolicySetResult
        policyUpdateReceiver.onReceive(this, intent);  // this refers to current context/activity
    }
}