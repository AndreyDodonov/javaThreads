package ru.engknow.homework_threads.deadlock;

/**
 *
 * @author Andrey Dodonov
 */
public class DeadLockDemo {

    private final Object lockA = new Object();
    private final Object lockB = new Object();

    public Thread[] start() {
        Thread thread1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("Thread 1: lock lockA");
                sleepSilently(100);
                System.out.println("Thread 1: try to lock lockB ...");
                synchronized (lockB) {
                    System.out.println("Thread 1: lock lockB");
                }
            }
        }, "Thread-1");

        Thread thread2 = new Thread(() -> {
            synchronized (lockB) {
                System.out.println("Thread 2: lock lockB");
                sleepSilently(100);
                System.out.println("Thread 2: try to lock lockA ...");
                synchronized (lockA) {
                    System.out.println("Thread 2: lock lockA");
                }
            }
        }, "Thread-2");

        thread1.setDaemon(true);
        thread2.setDaemon(true);

        thread1.start();
        thread2.start();

        return new Thread[]{thread1, thread2};
    }

    private void sleepSilently(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
