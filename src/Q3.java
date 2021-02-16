// 3. WAP to produce NoClassDefFoundError and ClassNotFoundException exception.


//After compilation, remove Temp.class from classpath.
class Temp {
    public void test() {
        System.out.println("Testing NoClassDefFoundError");
    }
}

public class Q3 {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("Test"); // Test.class does not exist.
        Temp temp = new Temp();
        temp.test();
    }
}
