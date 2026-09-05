package alternating;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import ru.engknow.homework_threads.alternating.AlternatingThreads;

/**
 *
 * @author Andrew Dodonov
 */
public class AlternatingThreadsTest {

    @Test
    @Timeout(5)
    void printsOneAndTwoAlternatelyStartingWithOne() throws InterruptedException {
        StringBuilder output = new StringBuilder();
        int limit = 20;

        AlternatingThreads printer = new AlternatingThreads(output::append, limit);
        Thread[] threads = printer.start();

        threads[0].join(2000);
        threads[1].join(2000);

        assertTrue(!threads[0].isAlive() && !threads[1].isAlive(),
                "Both threads should finish once the print limit is reached");

        String result = output.toString();
        assertEquals(limit, result.length(),
                "Should print exactly 'limit' symbols total");

        assertTrue(result.startsWith("1"), "Sequence must start with 1");
        assertEquals("12121212121212121212", result,
                "Symbols must strictly alternate 1/2");

    }

}
