/*

Q6.  Return a Future from ExecutorService by using callable and use get(), isDone(),
   isCancelled() with the Future object to know the status of task submitted.

 */

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Q6 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<Integer> result = getFuture();

        System.out.println("isDone(): " + result.isDone());
        System.out.println("isCancelled(): " + result.isCancelled());

        System.out.println("Duration: " + result.get());

        System.out.println("isDone(): " + result.isDone());
        System.out.println("isCancelled(): " + result.isCancelled());
    }

    private static Future<Integer> getFuture() {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<Integer> result = executorService.submit(() -> {
            Random random = new Random();
            int duration = random.nextInt(4000);
            System.out.println("Sleeping for " + duration + " milli seconds...");
            Thread.sleep(duration);
            System.out.println("Done sleeping.");
            return duration;
        });
        executorService.shutdown();
        return result;
    }
}
