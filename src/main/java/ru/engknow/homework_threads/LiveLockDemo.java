package ru.engknow.homework_threads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Andrey Dodonov
 */
public class LiveLockDemo {

    private final ReentrantLock lockA = new ReentrantLock();
    private final ReentrantLock lockB = new ReentrantLock();

    private final CyclicBarrier barrier = new CyclicBarrier(2);

    private volatile boolean running = true;

    public Thread[] start() {
        Thread thread1 = new Thread(()
                -> worker("Thread-1", lockA, lockB), "Thread-1"
        );

        Thread thread2 = new Thread(()
                -> worker("Thread-2", lockA, lockB), "Thread-2"
        );

        thread1.setDaemon(true);
        thread2.setDaemon(true);

        thread1.start();
        thread2.start();

        return new Thread[]{thread1, thread2};
    }

    private void worker(
            String name,
            ReentrantLock myLock,
            ReentrantLock otherLock) {
        while (running) {
            myLock.lock();
            try {
                System.out.println(name
                        + ": acquired my own lock, about to try the other one");
                barrier.await();
                if (otherLock.tryLock()) {
                    try {
                        System.out.println(name
                                + ": got BOTH locks — real progress made!");
                        running = false;
                        return;
                    } finally {
                        otherLock.unlock();
                    }
                } else {
                    System.out.println(name
                            + ": other lock is busy, releasing mine and retrying...");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            } catch (BrokenBarrierException e) {
                return;
            } finally {
                myLock.unlock();
            }
            sleepSilently(200);
        }
    }

    public void stop() {
        running = false;
        barrier.reset();
    }

    private void sleepSilently(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
