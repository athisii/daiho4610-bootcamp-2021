/* Q4. Calculate the number & Percentage Of Lowercase Letters,Uppercase Letters,
       Digits And Other Special Characters In A String.
*/

public class Q4 {

    private static void calculateNumberAndPercentage(final String inputString) {
        int upper = 0, lower = 0, number = 0, special = 0, len = inputString.length();

        for (int i = 0; i < len; i++) {
            char ch = inputString.charAt(i);
            if (ch >= 'A' && ch <= 'Z')
                upper++;
            else if (ch >= 'a' && ch <= 'z')
                lower++;
            else if (ch >= '0' && ch <= '9')
                number++;
            else
                special++;
        }

        System.out.println("Lowercase Letter:" + "\n\tNumber: " + lower + "\n\tPercentage: " + (lower * 100) / len);
        System.out.println("Uppercase Letter:" + "\n\tNumber: " + upper + "\n\tPercentage: " + (upper * 100) / len);
        System.out.println("Digit:"+ "\n\tNumber: " + number + "\n\tPercentage: " + (number * 100) / len);
        System.out.println("Special Characters:" + "\n\tNumber: " + special + "\n\tPercentage: " + (special * 100) / len);
    }

    public static void main(String[] args) {
        calculateNumberAndPercentage("#Java@Me12$");

    }
}
