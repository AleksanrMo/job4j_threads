package ru.job4j.concurrent.pools;

import ru.job4j.concurrent.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;


public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(11);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(
                    () -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                tasks.poll().run();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            ));
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public  void i() throws InterruptedException {
        for (int i = 0; i < tasks.getSize() - 1; i++) {
            threads.add(new Thread(tasks.poll()));
        }
        for (Thread t: threads) {
            t.start();
        }
    }

    public  void shutdown() throws InterruptedException {
        for (Thread t: threads) {
            t.interrupt();
            t.join();
        }

    }
    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        pool.work(() -> System.out.println("1"));
        pool.work(() -> System.out.println("2"));
        pool.work(() -> System.out.println("3"));
        pool.work(() -> System.out.println("4"));
        pool.shutdown();
    }
}
