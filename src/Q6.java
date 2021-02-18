//Create and access default and static method of an interface.

interface DefaultStatic {
    default void defaultMethod() {
        System.out.println("Default method of interface.");
    }

    static void staticMethod() {
        System.out.println("Static method of interface.");
    }

}


public class Q6 implements DefaultStatic {
    public static void main(String[] args) {
        DefaultStatic defaultStatic = new Q6();
        defaultStatic.defaultMethod();
        DefaultStatic.staticMethod();

    }
}
