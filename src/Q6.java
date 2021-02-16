//6. WAP showing try, multi-catch and finally blocks.

public class Q6 {
    public static void main(String[] args) {
        try {
            int[] array = new int[4];
            array[4] = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("Arithmetic Exception occurs");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ArrayIndexOutOfBounds Exception occurs");
        } catch (Exception e) {
            System.out.println("Exception occurs");
        } finally {
            System.out.println("Finally block is always executed");
        }

    }

}
