public class StackWalker_getInstance {
    public void test_StackWalker_getInstance() {
        StackWalker stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE); 
    
        System.out.println("Using StackWalker to traverse the stack trace:");
    
        stackWalker.forEach(stackFrame -> System.out.println("Class: " + stackFrame.getClassName()
            + "\nMethod: " + stackFrame.getMethodName()
            + "\nLine Number: " + stackFrame.getLineNumber()));
    
        System.out.println();
    
        callAnotherMethod();
    }
    
    public void callAnotherMethod() {
        System.out.println("Inside callAnotherMethod, let's print the Stack Trace again:");
    
        StackWalker stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE); 
    
        stackWalker.forEach(stackFrame -> System.out.println("Class: " + stackFrame.getClassName()
            + "\nMethod: " + stackFrame.getMethodName()
            + "\nLine Number: " + stackFrame.getLineNumber()));
    }
}