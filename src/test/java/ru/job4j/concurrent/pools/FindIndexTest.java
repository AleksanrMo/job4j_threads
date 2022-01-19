package ru.job4j.concurrent.pools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FindIndexTest {

    @Test
    void whenElementHaveIndex0() {
        Integer[] ints = new Integer[]{4, 1, 2, 0};
        int index = FindIndex.run(ints, 4);
        Assertions.assertEquals(0, index);
    }

    @Test
    void whenNoElementInArray() {
        Integer[] ints = new Integer[]{4, 1, 2};
         assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
             int index = FindIndex.run(ints, 5);
        });
    }
}