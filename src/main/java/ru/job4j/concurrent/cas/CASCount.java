package ru.job4j.concurrent.cas;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {


    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
       if (!count.compareAndSet(count.get(), count.get() + 1)) {
           throw new UnsupportedOperationException("Count is not impl.");
       }

    }

    public int get() {
       if (count.get() == null) {
            throw new UnsupportedOperationException("Count is not impl.");
       }
        return count.get();
    }
}
