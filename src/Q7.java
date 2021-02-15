//Q7. Write a program to print your Firstname,LastName & age using static block,
//  static method & static variable respectively

public class Q7 {
    private static String firstName;
    private static String lastName = "Ekhe";
    private static int age = 24;

    static {
        firstName = "Athisii";
        System.out.println("Firstname: " + firstName);
    }

    private static void printLastName() {
        System.out.println("Lastname: " + lastName);
    }

    public static void main(String[] args) {
        printLastName();
        System.out.println("Age: " + age);
    }

}
