package ru.job4j.concurrent;

/**
 * При каждом запуске нити запускаются произвольно т.е вывод идет в произвольном порядке.
 */
public class ConcurrentOutput {

    public static void main(String[] args) {

        Thread another = new Thread(
                () -> System.out.println(
                        Thread.currentThread().getName()));
        Thread second = new Thread(() -> System.out.println(
                Thread.currentThread().getName()));

        another.start();
        second.start();
    }
}
