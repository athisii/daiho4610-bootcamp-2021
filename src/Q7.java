//Override the default method of the interface.

interface OverrideDefault {
    default void defaultMethod() {
        System.out.println("Inside OverrideDefault");
    }
}

public class Q7 implements OverrideDefault {
    @Override
    public void defaultMethod() {
        System.out.println("Inside Q7");
    }

    public static void main(String[] args) {
        OverrideDefault overrideDefault = new Q7();
        overrideDefault.defaultMethod();
    }
}
