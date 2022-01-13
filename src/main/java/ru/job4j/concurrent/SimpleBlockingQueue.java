package ru.job4j.concurrent;

import org.junit.runner.notification.RunListener;

import javax.annotation.concurrent.GuardedBy;
import java.util.LinkedList;
import java.util.Queue;

@RunListener.ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int  limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized int getSize() {
       return queue.size();
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= limit) {
                    wait();
            }
        queue.add(value);
        notifyAll();
            }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
                wait();
        }

        notifyAll();
        return queue.remove();
    }
}
