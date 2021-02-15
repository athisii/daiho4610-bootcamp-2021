// Q8. Write a program to reverse a string and remove character
// from index 4 to index 9 from the reversed string using String Buffer

public class Q8 {

    public static void main(String[] args) {
        StringBuffer inputSB = new StringBuffer("Hello World From Java");
        inputSB.reverse();
        inputSB.delete(4, 10);
        System.out.println(inputSB);
    }

}
