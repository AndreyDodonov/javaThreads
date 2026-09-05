package ru.engknow.homework_threads.livelock;

import ru.engknow.homework_threads.livelock.LiveLockDemo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 *
 * @author Andrey Dodonov
 */
class LiveLockDemoTest {

    @Test
    @Timeout(5)
    void threadsShouldNeverBothAcquireBothLocks() throws InterruptedException {
        LiveLockDemo liveLock = new LiveLockDemo();
        Thread[] threads = liveLock.start();

        Thread thread1 = threads[0];
        Thread thread2 = threads[1];

        try {
            thread1.join(500);
            thread2.join(500);

            assertTrue(thread1.isAlive(),
                    "Thread 1 should still be retrying (livelock), not finished");
            assertTrue(thread2.isAlive(),
                    "Thread 2 should still be retrying (livelock), not finished");

        } finally {
            liveLock.stop();
        }
    }

}
