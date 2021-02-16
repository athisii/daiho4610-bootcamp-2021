// Q13. Create a custom exception that do not have any stack trace.

class CustomException extends Exception {
    public CustomException(String message) {
        super(message,null, true,false);
    }
}

public class Q13 {
    public static void main(String[] args) {
        try {
            throw new CustomException("Custom Exception");
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }
}
