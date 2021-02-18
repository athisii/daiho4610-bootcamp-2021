//Q12. Find the first even number in the integer list which is greater than 3.

import java.util.List;

public class Q12 {

    private static Integer findFirstEvenNumberGreaterThan3(List<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number % 2 == 0 && number > 3)
                .findFirst().orElse(null);
    }

    public static void main(String[] args) {

        System.out.println("First even number > 3: " +
                findFirstEvenNumberGreaterThan3(List.of(2, 3, 6, 8, 1, 5)));

    }
}
