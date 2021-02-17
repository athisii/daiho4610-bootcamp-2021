// Q2. Write a method that takes a string and returns the number of unique characters in the string.

public class Q2 {
    private static int countUniqueCharacters(String inputString) {
        return (int) inputString.chars()
                .distinct()
                .count();
    }

    public static void main(String[] args) {
        System.out.println("Number of unique characters: " + countUniqueCharacters("abcabc"));
    }

}
