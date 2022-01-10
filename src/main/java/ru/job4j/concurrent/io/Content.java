package ru.job4j.concurrent.io;

import java.util.function.Predicate;

public interface Content {

    String getContent(Predicate<Character> filter);

}
