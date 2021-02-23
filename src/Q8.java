//Schedule task using schedule(), scheduleAtFixedRate() and scheduleAtFixedDelay()

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Q8 {

    private static Runnable task() {
        return () -> System.out.println("Executing task at " + System.nanoTime());
    }

    private static void scheduleMethod() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        System.out.println("Submitting task at " + System.nanoTime() + " to be executed after 3 seconds.");
        scheduledExecutorService.schedule(task(), 3, TimeUnit.SECONDS);
        scheduledExecutorService.shutdown();
    }


    private static void scheduleAtFixedRateMethod() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        System.out.println("Scheduling task to be executed every 3 seconds with an initial delay of 0 seconds");
        for (int id = 0; id < 1; id++) {
            scheduledExecutorService.scheduleAtFixedRate(task(), 0, 3, TimeUnit.SECONDS);
        }
    }

    public static Runnable delayTask() {
        return () -> {
            Random random = new Random();
            int duration = random.nextInt(5000);
            try {
                System.out.println("Sleeping for " + duration + " milli seconds.");
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Waiting for 4 seconds to start next task.");
        };
    }

    private static void scheduleWithFixedDelayMethod() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        System.out.println("Scheduling task to be executed with delay of 4 seconds after every completion of task.");

        scheduledExecutorService.scheduleWithFixedDelay(delayTask(), 0, 4, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
//        scheduleMethod();
//        scheduleAtFixedRateMethod();
        scheduleWithFixedDelayMethod();
    }
}
