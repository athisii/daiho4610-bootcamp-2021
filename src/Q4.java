//Q4. Try shutdown() and shutdownNow() and observe the difference.

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Q4 {

    private static void shutDownMethod() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 3; i++) {
            executorService.submit(new Process(i)); // Process defined in Q3.
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);

    }

    private static void shutDownNowMethod() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 3; i++) {
            executorService.submit(new Process(i));
        }
        executorService.shutdownNow();

    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Testing shutdown() and shutdownNow()");
        /*
            shutdown() - when shutdown() method is called on an executor service,
                    it stops accepting new tasks, waits for previously submitted tasks to execute,
                    and then terminates the executor.

            shutdownNow() - this method interrupts the running task and shuts down the executor immediately.
        */
        shutDownMethod();
        shutDownNowMethod();
    }

}
