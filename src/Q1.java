/*
  Q1. Write the following a functional interface and implement it using lambda:
        (1) First number is greater than second number or not         Parameter (int ,int ) Return boolean
        (2) Increment the number by 1 and return incremented value    Parameter (int) Return int
        (3) Concatenation of 2 string                                 Parameter (String , String ) Return (String)
        (4) Convert a string to uppercase and return .                Parameter (String) Return (String)

 */

@FunctionalInterface
interface Greater {
    boolean isGreater(int number1, int number2);
}

@FunctionalInterface
interface Incrementer {
    int increment(int number);
}

@FunctionalInterface
interface Concatenate {
    String concatenate(String s1, String s2);
}

@FunctionalInterface
interface Uppercase {
    String toUppercase(String s);
}

public class Q1 {
    public static void main(String[] args) {
        Greater greater = (number1, number2) -> number1 > number2;
        Incrementer incrementer = number -> ++number;
        Concatenate concatenate = (string1, string2) -> string1 + string2;
        Uppercase uppercase = string -> string.toUpperCase();

        //Test Code
        System.out.println("First number > Second number? : " + greater.isGreater(3, 2));
        System.out.println("Incremented value: " + incrementer.increment(3));
        System.out.println("Concatenated String: " + concatenate.concatenate("Hello ", "Java"));
        System.out.println("Uppercase: " + uppercase.toUppercase("tothenew"));

    }
}
