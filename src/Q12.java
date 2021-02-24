//Q12. Use Atomic Classes instead of Synchronize method and blocks.

import java.util.concurrent.atomic.AtomicInteger;

class SafeAtomicClass {
    private final AtomicInteger count = new AtomicInteger(0);

    private void increment() {
        count.getAndIncrement();

    }

    public void doWork() throws InterruptedException {

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

}


public class Q12 {
    public static void main(String[] args) throws InterruptedException {
        SafeAtomicClass safeAtomicClass = new SafeAtomicClass();
        safeAtomicClass.doWork();
    }

}
