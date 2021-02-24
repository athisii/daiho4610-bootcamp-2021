//Q11. Use Synchronize block to enable synchronization between multiple threads trying to access method at same time.

public class Q11 {

    private int count = 0;

    private void increment() {
        synchronized (this) {
            count++;
        }
    }

    private void doWork() throws InterruptedException {
        Thread myThread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                increment();
            }
        });

        Thread myThread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                increment();
            }
        });
        myThread1.start();
        myThread2.start();
        myThread1.join();
        myThread2.join();
        System.out.println("Count : " + count);
    }

    public static void main(String[] args) throws InterruptedException {
        Q11 q11 = new Q11();
        q11.doWork();
    }
}
