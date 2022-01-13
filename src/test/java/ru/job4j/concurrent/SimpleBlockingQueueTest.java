package ru.job4j.concurrent;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {

    @Test
    public void test() throws InterruptedException {
        SimpleBlockingQueue<Integer> simple = new SimpleBlockingQueue<>();
        Thread thread1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                for (int i = 0; i < 30; i++) {
                    simple.offer(i);
                }
            }

        });
        Thread thread2 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                for (int i = 0; i < 30; i++) {
                    simple.poll();
                }
            }

        });
        thread1.start();
        thread2.start();
        thread1.interrupt();
        thread2.interrupt();
        thread1.join();
        thread2.join();

        assertThat(simple.getQueue().size(), is(0));

    }

}