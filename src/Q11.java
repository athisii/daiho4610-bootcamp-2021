// Q11. Find average of the number inside integer list after doubling it.

import java.util.List;

public class Q11 {

    private static double averageAfterDoubled(List<Integer> numbers) {
        return numbers.stream()
                .map(number -> number * 2)
                .mapToInt(number -> number)
                .average()
                .getAsDouble();
    }

    public static void main(String[] args) {

        System.out.println("Average: " +
                averageAfterDoubled(List.of(2, 3, 4, 5, 1)));
    }
}
