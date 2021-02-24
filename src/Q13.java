//Q13. Coordinate 2 threads using wait() and notify().

import java.util.Scanner;

class Processor {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer thread running....");
            wait();
            System.out.println("Resumed.");
        }
    }

    public void consume() throws InterruptedException {
        Scanner input = new Scanner(System.in);
        Thread.sleep(2000);
        synchronized (this) {
            System.out.println("Waiting for return key.");
            input.nextLine();
            System.out.println("Return key pressed.");
            notify();
            Thread.sleep(1000);
        }

    }
}


public class Q13 {

    private static void waitAndNotify() throws InterruptedException {
        Processor processor = new Processor();

        Thread thread1 = new Thread(() -> {
            try {
                processor.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                processor.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    public static void main(String[] args) throws InterruptedException {
        waitAndNotify();

    }


}
