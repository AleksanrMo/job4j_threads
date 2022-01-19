package ru.job4j.concurrent.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FindIndex<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T element;
    private final int to;
    private final int from;

    public FindIndex(T[] array, T element, int from, int to) {
        this.array =  array;
        this.element =  element;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        int size = to - from;
        if (size <= 10) {
           return result();

        }
            int mid =  (to + from) / 2;
            FindIndex<T> left = new FindIndex<>(array, element, from, mid);
            FindIndex<T> right = new FindIndex<>(array, element, mid + 1, to);
            left.fork();
            right.fork();
            int leftR = left.join();
            int rightR = right.join();
            return Math.max(rightR, leftR);
    }

    private int result() {
        for (int i = from; i < to; i++) {
            if (array[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    public static int run(Integer[] ints, int element) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        FindIndex<Integer> find = new FindIndex<>(ints, element, 0, ints.length);
        return pool.invoke(find);
    }
}
