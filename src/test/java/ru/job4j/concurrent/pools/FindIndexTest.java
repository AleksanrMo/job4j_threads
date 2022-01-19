package ru.job4j.concurrent.pools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FindIndexTest {

    @Test
    void whenElementHaveIndex0() {
        Integer[] ints = new Integer[]{4, 1, 2, 0};
        int index = FindIndex.run(ints, 4);
        Assertions.assertEquals(0, index);
    }

    @Test
    void whenNoElementInArray() {
        Integer[] ints = new Integer[135];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = i;
        }
        int index = FindIndex.run(ints, 135);
        Assertions.assertEquals(-1, index);
    }

    @Test
    void whenArrayHaveMoreThen30Elements() {
        Integer[] ints = new Integer[135];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = i;
        }
        int index = FindIndex.run(ints, 120);
        Assertions.assertEquals(120, index);
    }

    @Test
    void whenFindLastElement() {
        Integer[] ints = new Integer[35];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = i;
        }
        int index = FindIndex.run(ints, 34);
        Assertions.assertEquals(34, index);
    }
}