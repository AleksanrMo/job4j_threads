package ru.job4j.concurrent;

public class CountBarrier {
    private final Object o = this;
    private  int count = 0;
    private final int total;

    public CountBarrier(int total) {
        this.total = total;
    }

    public void count() {
        synchronized (o) {
            count++;
            o.notifyAll();

            }
    }

    public void await() {
        synchronized (o) {
            while (count < total) {
                try {
                    o.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
