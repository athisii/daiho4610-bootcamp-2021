//Write a program to format date as example "21-March-2016"

import java.text.DateFormat;
import java.util.Date;

public class Q8 {
    public static void main(String[] args) {
        Date currentDate = new Date();
        String dateToStr = DateFormat.getDateInstance().format(currentDate);
        System.out.println("Date: " + dateToStr);
    }
}
