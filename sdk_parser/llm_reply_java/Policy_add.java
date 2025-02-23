public class Policy_add {
    public void test_Policy_add() {
        // Gets the system-wide security policy
        Policy policy = Policy.getPolicy();

        if (policy != null) {
            System.out.println("Policy is: " + policy.toString());
        } else {
            System.out.println("No policy installed.");
        }
    }
}