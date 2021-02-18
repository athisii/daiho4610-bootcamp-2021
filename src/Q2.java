//Create a functional interface whose method takes 2 integers and return one integer.

@FunctionalInterface
interface Add {
    int add(int number1, int number2);
}

public class Q2 {
    public static void main(String[] args) {
        Add add = (number1, number2) -> number1 + number2;

        //Test code
        System.out.println(add.add(1, 2));
    }
}
