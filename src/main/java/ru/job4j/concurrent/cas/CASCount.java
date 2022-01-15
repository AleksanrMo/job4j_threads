package ru.job4j.concurrent.cas;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {


    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int it;
        do {
           it = count.get() + 1;
        } while (!count.compareAndSet(count.get(), it));

    }

    public int get() {
        return count.get();
    }
}
