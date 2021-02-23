//Q5. Use isShutDown() and isTerminated() with ExecutorService.

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Q5 {

    private static Runnable task(int id) {
        return () -> {
            System.out.println("Starting Task: " + id);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Completed Task: " + id);
        };
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(task(1));
        executorService.submit(task(2));
        executorService.shutdown();


        System.out.println("isShutdown: "+ executorService.isShutdown());
        System.out.println("isTerminated: " + executorService.isTerminated());

        executorService.awaitTermination(1, TimeUnit.DAYS);

        System.out.println("isShutdown: " + executorService.isShutdown());
        System.out.println("isTerminated: " + executorService.isTerminated());


    }
}
