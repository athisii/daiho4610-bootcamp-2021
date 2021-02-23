//Q2. Use sleep and join methods with thread.

public class Q2 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Sleeping for 500 milli seconds ....");
            }
        });
        thread.start();
        thread.join();
        System.out.println("Finished");
    }
}
