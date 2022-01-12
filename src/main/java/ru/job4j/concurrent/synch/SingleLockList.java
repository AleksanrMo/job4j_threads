package ru.job4j.concurrent.synch;

import net.jcip.annotations.GuardedBy;
import org.junit.runner.notification.RunListener;

import java.util.*;

@RunListener.ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

@GuardedBy("this")
    private final List<T> list;


    public SingleLockList(List<T> list) {
        this.list = list;
    }

    public synchronized List<T> copy() {
        return new ArrayList<>(this.list);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }
    public synchronized void add(T value) {
        list.add(value);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy().iterator();
    }
}
