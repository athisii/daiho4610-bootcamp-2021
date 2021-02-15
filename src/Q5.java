//Q5. Find common elements between two arrays.


import java.util.HashSet;
import java.util.Set;

public class Q5 {
    private static void commonElement(final Integer[] array1, final Integer[] array2) {
        Set<Integer> set = new HashSet<>();

        for (int element1 : array1) {
            for (int element2 : array2) {
                if (element1 == element2) {
                    set.add(element1);
                }
            }
        }

        System.out.println(set);
    }

    public static void main(String[] args) {
        Integer[] array1 = {2, 4, 5, 6};
        Integer[] array2 = {1, 4, 6, 7};

        commonElement(array1, array2);

    }
}
