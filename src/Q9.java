// Q9. Collect all the even numbers from an integer list.


import java.util.List;
import java.util.stream.Collectors;

public class Q9 {

    private static List<Integer> collectEvenNumbers(List<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number % 2 == 0)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {

        var even = collectEvenNumbers(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        System.out.println(even);

    }
}
