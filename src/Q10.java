//Q10. Use Synchronize method to enable synchronization between multiple threads trying to access method at same time.


public class Q10 {
    private int count = 0;

    private synchronized void increment() {
        count++;
    }

    private void doWork() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                increment();
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("Count : " + count);
    }

    public static void main(String[] args) throws InterruptedException {
        Q10 q10 = new Q10();
        q10.doWork();
    }

}
