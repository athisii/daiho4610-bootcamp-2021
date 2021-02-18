import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/*
Q5. Implement following functional interfaces from java.util.function using lambdas:
        (1) Consumer

        (2) Supplier

        (3) Predicate

        (4) Function

 */
public class Q5 {
    public static void main(String[] args) {
        //1
        Consumer<String> consumer = input -> System.out.println(input);
        consumer.accept("Hello World");

        //2
        Supplier<Double> supplier = () -> Math.random();
        System.out.println(supplier.get());

        //3
        Predicate<Integer> predicate = number -> number > 10;
        System.out.println(predicate.test(11));

        //4
        Function<Integer, Integer> function = number -> number * number;
        System.out.println(function.apply(11));
    }
}

