package ru.job4j.concurrent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimpleBlockingQueueTest {

    @Test
    public void whenTwoThreadsWorking() throws InterruptedException {
        SimpleBlockingQueue<Integer> simple = new SimpleBlockingQueue<>();
        Thread thread1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
              try {
                  for (int i = 0; i < 30; i++) {
                      simple.offer(i);
                  }
              } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
              }
            }
        });
        Thread thread2 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
              try {
                  for (int i = 0; i < 30; i++) {
                      simple.poll();
                  }
              } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
              }
            }
        });
        thread1.start();
        thread2.start();
        thread1.interrupt();
        thread2.interrupt();
        thread1.join();
        thread2.join();
        Assertions.assertEquals(simple.getQueue().size(), (0));
    }

}