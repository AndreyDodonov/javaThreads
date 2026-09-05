package ru.engknow.homework_threads.alternating;

import java.util.function.Consumer;

/**
 *
 * @author Andrey Dodonov
 */
public class AlternatingThreads {

    private final Object lock = new Object();
    private final Consumer<String> sink;

    private final int totalPrintsLimit; // for tests

    private volatile boolean running = true;
    private int printedCount = 0;

    private boolean oneIsNext = true;

    public AlternatingThreads(Consumer<String> sink, int totalPrintsLimit) {
        this.sink = sink;
        this.totalPrintsLimit = totalPrintsLimit;
    }

    public AlternatingThreads() {
        this(s -> System.out.print(s), 0);
    }

    public Thread[] start() {
        Thread printerOne = new Thread(() -> printLoop(true), "Printer-1");
        Thread printerTwo = new Thread(() -> printLoop(false), "Printer-2");

        printerOne.setDaemon(true);
        printerTwo.setDaemon(true);

        printerOne.start();
        printerTwo.start();

        return new Thread[]{printerOne, printerTwo};
    }

    private void printLoop(boolean isOne) {
        while (true) {
            synchronized (lock) {
                while (running && oneIsNext != isOne) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                if (!running) {
                    return;
                }

                if (totalPrintsLimit > 0 && printedCount >= totalPrintsLimit) {
                    running = false;
                    lock.notifyAll();
                    return;
                }

                sink.accept(isOne ? "1" : "2");
                printedCount++;
                oneIsNext = !oneIsNext;

                lock.notifyAll();
            }
        }
    }
}
