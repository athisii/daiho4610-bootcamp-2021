//Q2. Write a program to find the number of occurrences of the duplicate words in a string and print them ?


import java.util.HashMap;
import java.util.Map;

public class Q2 {

    private static void duplicateWords(String inputString) {
        final String[] words = inputString.split(" ");
        final Map<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            if (wordCount.containsKey(word)) wordCount.put(word, wordCount.get(word) + 1);
            else wordCount.put(word, 1);
        }
        wordCount.forEach((k, v) -> {
            if (v > 1) System.out.println(k + " : " + v);
        });
    }

    public static void main(String[] args) {
        duplicateWords("Java Python Java Python C++ C Javascript");
    }
}
