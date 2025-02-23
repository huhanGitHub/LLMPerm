public class URLClassLoader_run {
    public static void test_URLClassLoader_run() {
        try {
            //path to the directory or JAR file
            File file = new File("/path/to/your/directory/or/JAR");

            //Convert File to a URL
            URL url = file.toURI().toURL();          
            URL[] urls = new URL[]{url};

            //Create a new class loader with the directory
            URLClassLoader clsLoader = new URLClassLoader(urls);

            //Load in the class; MyClass.class should be located in
            //the directory or file file:/path/to/your/directory/MyClass.class
            Class cls = clsLoader.loadClass("MyClass");

            //Create a new instance from the loaded class
            Object obj = cls.newInstance();

            //Getting a method from the loaded class and invoking it
            Method method = cls.getDeclaredMethod("myMethod");
            method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}