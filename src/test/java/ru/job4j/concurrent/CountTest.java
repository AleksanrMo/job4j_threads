package ru.job4j.concurrent;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;

public class CountTest {


    private class ThreadCount extends Thread {

        private final Count count;
        private ThreadCount(final Count count) {
            this.count = count;
        }
        @Override
        public void run() {
            this.count.increment();
        }
    }

    @Test
    public void test() throws InterruptedException {
        Count count = new Count();
        Thread t1 = new ThreadCount(count);
        Thread t2 =  new ThreadCount(count);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        Assertions.assertEquals(count.getValue(), 2);
    }
}