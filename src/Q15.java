//q15. Use Reentrant lock for coordinating 2 threads with signal(), signalAll() and wait().

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Runner {
    private int count = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private void increment() {
        for (int i = 0; i < 100; i++) {
            count++;
        }
    }

    public void awaitThread() throws InterruptedException {
        lock.lock();
        System.out.println("Waiting....");
        condition.await();
        System.out.println("Woken up!");
        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void signalThread() throws InterruptedException {
        Thread.sleep(1000); //Just to make sure firstThread starts first.
        lock.lock();
        System.out.println("Press the return key!");
        new Scanner(System.in).nextLine();
        System.out.println("Got return key!");
        condition.signal();
        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void signalAllThread() throws InterruptedException {
        Thread.sleep(1000); //Just to make sure firstThread starts first.
        lock.lock();
        System.out.println("Press the return key!");
        new Scanner(System.in).nextLine();
        System.out.println("Got return key!");
        condition.signalAll();
        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void finished() {
        System.out.println("Count: " + count);
    }
}

public class Q15 {

    private static void signalAwait() throws InterruptedException {
        Runner runner = new Runner();
        Thread thread1 = new Thread(() -> {
            try {
                runner.awaitThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                runner.signalThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        runner.finished();
    }

    private static void signalAllAwait(int numberOfThreads) throws InterruptedException {
        Runner runner = new Runner();

        for (int id = 0; id < numberOfThreads; id++) {
            new Thread(() -> {
                try {
                    runner.awaitThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Thread thread2 = new Thread(() -> {
            try {
                runner.signalAllThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread2.start();


    }

    public static void main(String[] args) throws InterruptedException {
        signalAwait();
//        signalAllAwait(5);

    }

}