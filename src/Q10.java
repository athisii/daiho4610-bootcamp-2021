// Q10. Sum all the numbers greater than 5 in the integer list.

import java.util.List;

public class Q10 {

    public static int sumNumbersGreaterThan5(List<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number > 5)
                .mapToInt(number -> number)
                .sum();
    }

    public static void main(String[] args) {

        System.out.println(sumNumbersGreaterThan5(List.of(4, 5, 6, 7, 10, 2, 1)));

    }
}