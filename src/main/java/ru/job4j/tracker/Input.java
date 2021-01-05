package ru.job4j.tracker;

/**
 * Интерфейс
 */
public interface Input {
    int askInt(String question, int range);

    int askInt(String question);

    String askStr(String question);
}
