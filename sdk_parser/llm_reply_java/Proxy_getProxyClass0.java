import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Proxy_getProxyClass0 {
    public void test_Proxy_getProxyClass0() { 
        // create an invocation handler
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("Method " + method.getName() + " is called");
                return null;
            }
        };

        // get the dynamic proxy class
        Class proxyClass = Proxy.getProxyClass(Thread.currentThread().getContextClassLoader(), Runnable.class);

        try {
            // dynamically create an instance
            Runnable runnableProxy;
            runnableProxy = (Runnable) proxyClass.getConstructor(new Class[] { InvocationHandler.class }).newInstance(new Object[] { handler });
            
            // call methods on the proxy instance
            runnableProxy.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}