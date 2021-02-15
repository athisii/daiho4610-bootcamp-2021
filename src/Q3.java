//Q3. Write a program to find the number of occurrences of a character in a string without using loop?

public class Q3 {
    private static int countOccurrenceOfCharacter(final String inputString, final char character) {
        return (int) inputString.codePoints()
                .filter(ch -> ch == character)
                .count();
    }

    public static void main(String[] args) {
        System.out.println(countOccurrenceOfCharacter("Java", 'a'));
    }
}
