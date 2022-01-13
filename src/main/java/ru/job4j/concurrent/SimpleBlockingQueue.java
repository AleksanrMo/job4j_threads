package ru.job4j.concurrent;

import org.junit.runner.notification.RunListener;

import javax.annotation.concurrent.GuardedBy;
import java.util.LinkedList;
import java.util.Queue;

@RunListener.ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private int  limit = 10;

    public synchronized Queue<T> getQueue() {
        return new LinkedList<>(queue);
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= limit) {
                    wait();
            }
        notifyAll();
        queue.add(value);



            }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
                wait();
        }
        notifyAll();
        return queue.remove();
    }
}
