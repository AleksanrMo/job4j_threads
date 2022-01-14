package ru.job4j.concurrent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

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
        Thread.sleep(1000);
        thread1.interrupt();
        thread2.interrupt();
        thread1.join();
        thread2.join();
        Assertions.assertEquals(simple.getSize(), 0);
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> IntStream.range(0, 5).forEach(l -> {
                  try {
                    queue.offer(l);
                } catch (InterruptedException ignored) {
                  }
            }));
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (queue.getSize() != 0 || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        Assertions.assertEquals(buffer, Arrays.asList(0, 1, 2, 3, 4));
    }
}