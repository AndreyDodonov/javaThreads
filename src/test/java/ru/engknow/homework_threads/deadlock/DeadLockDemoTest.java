package ru.engknow.homework_threads.deadlock;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import ru.engknow.homework_threads.deadlock.DeadLockDemo;

/**
 *
 * @author Andrey Dodonov
 */
class DeadLockDemoTest {

    @Test
    @Timeout(5)
    void threadsShouldHangForeverDueToDeadlock() throws InterruptedException {
        DeadLockDemo deadLock = new DeadLockDemo();
        Thread[] threads = deadLock.start();

        Thread thread1 = threads[0];
        Thread thread2 = threads[1];

        thread1.join(1000);
        thread2.join(1000);
        
        assertTrue(thread1.isAlive(), "Поток 1 должен зависнуть в deadlock, а не завершиться");
        assertTrue(thread2.isAlive(), "Поток 1 должен зависнуть в deadlock, а не завершиться");
    }

}
