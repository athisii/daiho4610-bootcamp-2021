//Q3. Using (instance) Method reference create and apply add and subtract method and
// using (Static) Method reference create and apply multiplication method for the functional interface created.


@FunctionalInterface
interface Adder {
    int add(int num1, int num2);
}

@FunctionalInterface
interface Subtract {
    int sub(int num1, int num2);
}

@FunctionalInterface
interface Multiplier {
    int mul(int num1, int num2);
}

class Calculator {
    public int add(int num1, int num2) {
        return num1 + num2;
    }

    public int sub(int num1, int num2) {
        return num1 - num2;
    }

    public static int mul(int num1, int num2) {
        return num1 * num2;
    }
}

public class Q3 {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Adder adder = calculator::add; // instance method reference
        Subtract subtract = calculator::sub; //instance method reference
        Multiplier multiplier = Calculator::mul; //Static method reference
    }
}
