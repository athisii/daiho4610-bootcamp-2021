import java.util.Scanner;

/*
   8. WAP to read words from the keyboard until the word done is entered.
     For each word except done, report whether its first character is equal to  its last character.
     For the required loop, use a
        a) while statement
        b) do-while statement
 */
public class Q8 {

    private static void whileMethod(Scanner input) {
        System.out.print("Enter a word: ");
        String word = input.next();

        while (!word.equals("done")) {
            if (word.charAt(0) == word.charAt(word.length() - 1)) {
                System.out.println("First and Last character are equals for the word: " + word);
            } else {
                System.out.println("First and Last character are Not equals for the word: " + word);
            }
            System.out.print("\nEnter a word: ");
            word = input.next();
        }
    }

    private static void doWhileMethod(Scanner input) {
        System.out.print("Enter a word: ");
        String word = input.next();
        do {
            if (word.charAt(0) == word.charAt(word.length() - 1)) {
                System.out.println("First and last character are equals for the word: " + word);
            } else {
                System.out.println("First and last character are Not equals for the word: " + word);
            }
            System.out.print("\nEnter a word: ");
            word = input.next();
        } while (!word.equals("done"));
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\n**************** SELECT YOUR NUMBER *********************");
            System.out.println("1. while Method. \n2. do-while Method. \n3. Quit");
            System.out.print("Enter your choice: ");
            String choice = input.next();
            switch (choice) {
                case "1" -> whileMethod(input);
                case "2" -> doWhileMethod(input);
                case "3" -> System.exit(0);
                default -> System.out.println("Invalid choice!");
            }
        }
    }

}
