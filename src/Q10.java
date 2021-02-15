/*
  Q10. Write a single program for following operation using overloading
      A) Adding 2 integer number
      B) Adding 2 double
      C) multiplying 2 float
      D) multiplying 2 int
      E) concat 2 string
      F) Concat 3 String
 */


public class Q10 {
    private static int add(final int number1, final int number2) {
        return number1 + number2;
    }

    private static double add(final double number1, final double number2) {
        return number1 + number2;
    }

    private static int multiply(final int number1, final int number2) {
        return number1 * number2;
    }

    private static float multiply(final float number1, final float number2) {

        return number1 * number2;
    }

    private static String concat(final String string1, final String string2) {
        return string1 + string2;
    }

    private static String concat(final String string1, final String string2, final String string3) {
        return string1 + string2 + string3;
    }


    public static void main(String[] args) {

        System.out.println(add(3, 4));
        System.out.println(add(3.3, 4.4));
        System.out.println(multiply(3, 4));
        System.out.println(multiply(3.3F, 4.4F));
        System.out.println(concat("Hello ", "Java"));
        System.out.println(concat("Hello ", "My ", "Java"));
    }

}
