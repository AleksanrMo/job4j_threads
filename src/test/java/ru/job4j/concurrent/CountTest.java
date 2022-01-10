package ru.job4j.concurrent;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


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
        assertThat(count.getValue(), is(2));
    }
}