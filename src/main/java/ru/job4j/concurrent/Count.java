package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import org.junit.runner.notification.RunListener;

@RunListener.ThreadSafe
public class Count {
    @GuardedBy("this")
    private int value;

    public synchronized void increment() {
         this.value++;
    }

    public synchronized int getValue() {
        return this.value;
    }

}
