//Q1. Create and Run a Thread using Runnable Interface and Thread class.


class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println("MyThread");
        }
    }
}

public class Q1 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new MyThread();
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("MyRunnableThread");
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Finished");
    }
}
