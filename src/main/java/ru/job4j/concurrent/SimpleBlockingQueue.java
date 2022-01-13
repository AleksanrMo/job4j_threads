package ru.job4j.concurrent;

import org.junit.runner.notification.RunListener;

import javax.annotation.concurrent.GuardedBy;
import java.util.LinkedList;
import java.util.Queue;

@RunListener.ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public synchronized Queue<T> getQueue() {
        return new LinkedList<>(queue);
    }

    public synchronized void offer(T value) {
        while (queue.size() >= 20) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        notify();
        queue.add(value);


            }

    public synchronized T poll() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        notify();
        return queue.remove();
    }
}
