package ru.job4j.concurrent.cahe;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CacheTest {

    @Test
    public void whenUpdateBaseReturnTrue() {
        Cache cache = new Cache();
        cache.add(new Base(0, 1));
        cache.add(new Base(1, 1));
        Assertions.assertTrue(cache.update(new Base(1, 1)));
    }

    @Test
    public void whenVersionIsChanged() {
        Cache cache = new Cache();
        Base base = new Base(0, 1);
        cache.add(base);
        base.setName("Alex");
        cache.update(base);
        Assertions.assertEquals(base.getVersion(), 2);
        Assertions.assertEquals(base.getName(), "Alex");
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateReturnFalse() {
        Cache cache = new Cache();
        Base base = new Base(0, 1);
        Base base2 =  new Base(1, 1);
        Base base3 =  new Base(1, 2);
        cache.add(base);
        cache.add(base2);
        cache.update(base3);
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base base = new Base(0, 1);
        Base base2 =  new Base(1, 1);
        cache.add(base);
        cache.add(base2);
        cache.delete(base);
        Assertions.assertEquals(cache.getSize(), 1);
    }

}