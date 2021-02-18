//Implement multiple inheritance with default method inside  interface.

interface Interface1 {
    default void defaultMethod() {
        System.out.println("Inside Interface1");
    }
}

interface Interface2 {
    default void defaultMethod() {
        System.out.println("Inside Interface2");
    }
}

public class Q8 implements Interface1, Interface2 {

    @Override
    public void defaultMethod() {
        Interface1.super.defaultMethod();
        Interface2.super.defaultMethod();
    }

    public static void main(String[] args) {
        Q8 q8 = new Q8();
        q8.defaultMethod();
    }
}
