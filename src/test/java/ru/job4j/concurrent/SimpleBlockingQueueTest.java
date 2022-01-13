package ru.job4j.concurrent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimpleBlockingQueueTest {

    @Test
    public void whenTwoThreadsWorking() throws InterruptedException {
        SimpleBlockingQueue<Integer> simple = new SimpleBlockingQueue<>(10);
        Thread thread1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
              try {
                  for (int i = 0; i < 10; i++) {
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
                  for (int i = 0; i < 10; i++) {
                      System.out.println(simple.poll());
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
        Assertions.assertEquals(simple.getSize(), 0);
    }

}