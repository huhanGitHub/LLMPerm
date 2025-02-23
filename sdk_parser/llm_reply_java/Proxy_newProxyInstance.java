public class Proxy_newProxyInstance {
    public void test_Proxy_newProxyInstance() {
        Class[] interfaces = new Class[]{Runnable.class};
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("run")) {
                    System.out.println("Proxy 'run' method called");
                    return null;
                }

                return method.invoke(proxy, args);
            }
        };

        // create proxy instance
        Runnable runnable = (Runnable) Proxy.newProxyInstance(Runnable.class.getClassLoader(), interfaces, handler);

        // now calling run method on proxy instance will lead to handler's invoke method.
        runnable.run();
    }
}