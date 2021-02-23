//Q3. Use a singleThreadExecutor to submit multiple threads.

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Process implements Runnable {
    private final int id;

    public Process(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Starting: " + id);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Completed: " + id);
    }
}


public class Q3 {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 3; i++) {
            executorService.submit(new Process(i));
        }

        executorService.shutdown();
        System.out.println("All tasks submitted");
        executorService.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("All tasks completed");

    }
}
