
/*
    Q2. WAP to sorting string without using string Methods?
*/


public class Q2 {
    private static String sortString(String inputString) {
        int j;
        char temp = 0;
        char[] chars = inputString.toCharArray();
        int len = chars.length;

        for (int i = 1; i < len; i++) {

            for (j = 0; j < len; j++) {

                if (chars[j] > chars[i]) {
                    temp = chars[i];
                    chars[i] = chars[j];
                    chars[j] = temp;
                }

            }

        }
        return String.valueOf(chars);
    }

    public static void main(String[] args) {
        System.out.println (sortString("sort"));
    }

}
