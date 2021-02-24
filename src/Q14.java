// Q14. Coordinate multiple threads using wait() and notifyAll()

class Proccessor {
    public void waitingTask(int id) throws InterruptedException {
        synchronized (this) {
            System.out.println("Starting Task" + id + "....");
            wait();
            System.out.println("Task" + id + " notified!");
        }
    }

    public void notifyAllTasks() throws InterruptedException {
        Thread.sleep(3000);
        synchronized (this) {
            System.out.println("Notifying all waiting task to wake up!");
            notifyAll();
            Thread.sleep(2000);
        }
    }
}

public class Q14 {

    private static void waitNotifyAll(int numberOfThreads) throws InterruptedException {
        Proccessor proccessor = new Proccessor();

        for (int id = 0; id < numberOfThreads; id++) {
            int finalId = id;
            new Thread(() -> {
                try {
                    proccessor.waitingTask(finalId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        proccessor.notifyAllTasks();
    }

    public static void main(String[] args) throws InterruptedException {

        waitNotifyAll(5);

    }
}
