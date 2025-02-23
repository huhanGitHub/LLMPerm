import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

public class AccessibleObject_setAccessible {

    private void privateMethod() {
        System.out.println("Private method executed!");
    }

    public void test_AccessibleObject_setAccessible() {
        try {
            Method privateMethod = AccessibleObject_setAccessible.class.getDeclaredMethod("privateMethod");
            AccessibleObject accessibleObject = (AccessibleObject) privateMethod;
            accessibleObject.setAccessible(true);
            privateMethod.invoke(this, null);
            accessibleObject.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception occurred while testing AccessibleObject's setAccessible method!");
        }
    }
}