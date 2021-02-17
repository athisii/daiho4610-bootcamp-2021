//Write a method that takes a string and print the number of occurrence of each character characters in the string.

import java.util.HashMap;
import java.util.Map;

public class Q3 {
    private static void countOccurrenceCharacter(String inputString) {
        final Map<Character, Integer> characterCount = new HashMap<>();
        for (char character : inputString.toCharArray()) {
            if (characterCount.containsKey(character)) {
                characterCount.put(character, characterCount.get(character) + 1);
            } else characterCount.put(character, 1);
        }
        characterCount.forEach((key, value) -> System.out.println("\t\t" + key + " : " + value));
    }

    public static void main(String[] args) {
        System.out.println("Character : Count");
        countOccurrenceCharacter("abcabcaab");
    }
}
