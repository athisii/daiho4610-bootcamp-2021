//Q9. Increase concurrency with Thread pools using newCachedThreadPool() and newFixedThreadPool().

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Q9 {

    public static Runnable task(int id) {
        return () -> {
            System.out.println("Starting task" + id + "...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Finished task" + id + "!");
        };
    }

    public static void cachedThreadPool(int numberOfTasks) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int id = 0; id < numberOfTasks; id++) {
            executorService.submit(task(id));
        }
        executorService.shutdown();
    }

    public static void fixedThreadPool(int numberOfTasks, int numberOfThreads) {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        for (int id = 0; id < numberOfTasks; id++) {
            executorService.submit(task(id));
        }
        executorService.shutdown();
    }

    public static void main(String[] args) {

//        cachedThreadPool(5);
        fixedThreadPool(5, 3);
    }
}
