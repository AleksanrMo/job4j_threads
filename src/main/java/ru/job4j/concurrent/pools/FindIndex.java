package ru.job4j.concurrent.pools;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

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

    public FindIndex(T[] array, T element) {
        this.array = array;
        this.element = element;
        this.from = 0;
        this.to = array.length;

    }

    @Override
    protected Integer compute() {
        int size = to - from;
        if (size <= 10) {
            for (int i = 0; i < to; i++) {
                if (array[i].equals(element)) {
                    return i;
                }
            }
        } else {
            int mid = (from + to) / 2;
            FindIndex<T> left = new FindIndex<>(array, element, from, mid);
            FindIndex<T> right = new FindIndex<>(array, element, mid + 1, to);
            left.fork();
            right.fork();
            int leftR = left.join();
            int rightR = right.join();
            return rightR == -1 ? leftR : rightR;
        }
        return -1;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Integer[] ints = new Integer[2000];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = i + 1;
        }
        ForkJoinPool pool = ForkJoinPool.commonPool();
        FindIndex<Integer> find = new FindIndex<>(ints, 35);
        int result = pool.invoke(find);
        System.out.println(result);
    }
}
