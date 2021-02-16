//7. WAP to convert seconds into days, hours, minutes and seconds.

public class Q7 {
    private static void convertSecToDHMS(int seconds) {
        int day = seconds / (24 * 3600);

        seconds = seconds % (24 * 3600);
        int hour = seconds / 3600;

        seconds %= 3600;
        int minutes = seconds / 60;

        seconds %= 60;
        int secs = seconds;

        System.out.println(day + " " + "day " + hour
                + " " + "hours " + minutes + " "
                + "minutes " + secs + " "
                + "seconds.");
    }

    public static void main(String[] args) {
        convertSecToDHMS(122333);
    }
}
