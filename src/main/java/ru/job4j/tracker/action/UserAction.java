package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.Store;

import java.util.function.Consumer;

/**
 * Интерфейс
 */
public interface UserAction {
    boolean execute(Input input, Store store, Consumer<String> output);
}
