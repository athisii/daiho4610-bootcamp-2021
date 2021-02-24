//Q16. Create a deadlock and Resolve it using tryLock().

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Account {
    private int balance = 10000;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

    public static void transfer(Account acc1, Account acc2, int amount) {
        acc1.withdraw(amount);
        acc2.deposit(amount);

    }
}

class AccountThread {
    private final Account account1 = new Account();
    private final Account account2 = new Account();
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();
    Random random = new Random();

    private void deadlockTransfer(Lock lock1, Lock lock2, Account account1, Account account2) {
        for (int i = 0; i < 100000; i++) {
            lock1.lock();
            lock2.lock();
            try {
                Account.transfer(account1, account2, random.nextInt(200));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    private void resolvedDeadlockTransfer(Lock lock1, Lock lock2, Account account1, Account account2) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            acquireLocks(lock1, lock2);
            try {

                Account.transfer(account1, account2, random.nextInt(200));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    private void acquireLocks(Lock firstLock, Lock secondLock) throws InterruptedException {
        //Acquire Lock
        boolean gotFirstLock = false;
        boolean gotSecondLock = false;
        while (true) {
            try {
                gotFirstLock = firstLock.tryLock();
                gotSecondLock = secondLock.tryLock();
            } finally {
                if (gotFirstLock && gotSecondLock) {
                    return;
                }
                if (gotFirstLock) {
                    firstLock.unlock();
                }
                if (gotSecondLock) {
                    secondLock.unlock();
                }
            }
            //Lock not acquired
            Thread.sleep(1);
        }
    }

    public void deadlockThread1() {
        //Transfer from account1->account2
        deadlockTransfer(lock1, lock2, account1, account2);
    }


    public void deadlockThread2() {

        //Transfer from account2->account1
        deadlockTransfer(lock2, lock1, account2, account1);
    }

    public void resolvedDeadlockThread1() throws InterruptedException {
        //Transfer from account1->account2
        resolvedDeadlockTransfer(lock1, lock2, account1, account2);
    }

    public void resolvedDeadlockThread2() throws InterruptedException {
        //Transfer from account2->account1
        resolvedDeadlockTransfer(lock2, lock1, account2, account1);
    }


    public void finished() {
        System.out.println("Account1 Balance: " + account1.getBalance());
        System.out.println("Account2 Balance: " + account2.getBalance());
        System.out.println("Total Balance: " + (account1.getBalance() + account2.getBalance()));
    }
}


public class Q16 {
    private static void createDeadlock(AccountThread accountThread) throws InterruptedException {
        Thread t1 = new Thread(accountThread::deadlockThread1);
        Thread t2 = new Thread(accountThread::deadlockThread2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        accountThread.finished();
    }

    private static void resolvedDeadlock(AccountThread accountThread) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                accountThread.resolvedDeadlockThread1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                accountThread.resolvedDeadlockThread2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        accountThread.finished();
    }

    public static void main(String[] args) throws InterruptedException {
        AccountThread accountThread = new AccountThread();
        //Test 1
//        createDeadlock(accountThread);
        //Test 2
        resolvedDeadlock(accountThread);

    }
}

