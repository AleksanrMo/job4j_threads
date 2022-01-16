package ru.job4j.concurrent.pools;

import net.jcip.annotations.GuardedBy;
import org.junit.runner.notification.RunListener;
import ru.job4j.concurrent.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

@RunListener.ThreadSafe
public class ThreadPool {

    @GuardedBy("this")
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public synchronized void i() throws InterruptedException {
        for (int i = 0; i < tasks.getSize() - 1; i++) {
            threads.add(new Thread(tasks.poll()));
        }
        for (Thread t: threads) {
            t.start();
        }
    }

    public synchronized void shutdown() throws InterruptedException {
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
        System.out.println(pool.tasks.getSize());
        pool.i();
        Thread.sleep(5000);
        pool.shutdown();

    }
}
