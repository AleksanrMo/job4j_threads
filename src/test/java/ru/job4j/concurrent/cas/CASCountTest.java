package ru.job4j.concurrent.cas;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CASCountTest {

    @Test
    public void whenIncrementTwo() {
        CASCount count = new CASCount();
        count.increment();
        count.increment();
        Assertions.assertEquals(count.get(), 2);
    }

}