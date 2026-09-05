/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package ru.engknow.homework_threads;

import ru.engknow.homework_threads.alternating.AlternatingThreads;
import ru.engknow.homework_threads.deadlock.DeadLockDemo;
import ru.engknow.homework_threads.livelock.LiveLockDemo;

/**
 *
 * @author user
 */
public class HomeWork_threads {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("-- Threads test start--");
        runDeadlockDemo();
        runLivelockDemo();
        runAlternationDemo();
        System.out.println("-- Threads test end--");

    }

    private static void runDeadlockDemo() throws InterruptedException {
        System.out.println("--- DeadLock demo ---");
        new DeadLockDemo().start();

        Thread.sleep(1000);
    }

    private static void runLivelockDemo() throws InterruptedException {
        System.out.println("--- LiveLock demo ---");
        LiveLockDemo liveDemo = new LiveLockDemo();
        liveDemo.start();

        Thread.sleep(2000);
        liveDemo.stop();
    }

    private static void runAlternationDemo() throws InterruptedException {
        System.out.println("--- Alternating 1/2 print demo ---");
        
        AlternatingThreads printer = new AlternatingThreads();
        printer.start();
        
        Thread.sleep(1000);
        printer.stop();
        
    }
}
