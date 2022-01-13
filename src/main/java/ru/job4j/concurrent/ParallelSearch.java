package ru.job4j.concurrent;

public class ParallelSearch {
    public static void main(String[] args) throws InterruptedException {

        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        for (int index = 0; index < 3; index++) {
                            try {
                                System.out.println(queue.poll());
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }
        );

       Thread thread = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                           for (int index = 0; index != 3; index++) {
                               try {
                                   queue.offer(index);
                               } catch (InterruptedException e) {
                                   Thread.currentThread().interrupt();
                               }
                           }
                    }

                }
        );
        consumer.start();
        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
        consumer.interrupt();
        thread.join();
        consumer.join();
    }
}
