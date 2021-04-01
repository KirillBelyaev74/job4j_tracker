package ru.job4j.tracker.input;

/**
 * Интерфейс
 */
public interface Input {
    int askInt(String question, int range);

    int askInt(String question);

    String askStr(String question);
}
