//Q9. Write a program to display times in different country format.


import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Q9 {
    public static void main(String[] args) {
        Date date = new Date();
        Locale[] locales = DateFormat.getAvailableLocales();
        for (Locale locale : locales) {
            System.out.println(DateFormat.getTimeInstance(DateFormat.FULL, locale).format(date));
        }
    }
}
