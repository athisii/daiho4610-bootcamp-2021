//Q7. Submit List of tasks to ExecutorService and wait for the completion of all the tasks.

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Q7 {
    private static Runnable task(int id) {
        return () -> {
            System.out.println("Starting Task " + id);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Completed Task " + id);
        };
    }

    private static ExecutorService createMultipleTasks(int numberOfTasks) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int id = 0; id < numberOfTasks; id++) {
            executorService.submit(task(id));
        }
        executorService.shutdown();
        return executorService;
    }

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = createMultipleTasks(5);
        System.out.println("All tasks submitted!");
        executorService.awaitTermination(5, TimeUnit.MINUTES);
        System.out.println("All tasks completed");
    }


}
