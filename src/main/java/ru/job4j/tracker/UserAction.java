package ru.job4j.tracker;

import java.util.function.Consumer;

/**
 * Интерфейс
 */
public interface UserAction {
    boolean execute(Input input, Store store, Consumer<String> output);
}
