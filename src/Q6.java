//Q6. There is an array with every element repeated twice except one. Find that element

public class Q6 {
    private static int findSingle(final int[] array) {
        int result = array[0];
        for (int i = 1; i < array.length; i++) {
            result = result ^ array[i];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = {3, 3, 1, 1, 4, 5, 6, 6, 5};
        System.out.println("Element occurring once: "+ findSingle(array));
    }
}
