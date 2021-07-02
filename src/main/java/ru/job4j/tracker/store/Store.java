package ru.job4j.tracker.store;

import ru.job4j.tracker.item.Item;

import java.util.List;
import java.util.function.Consumer;

public interface Store extends AutoCloseable {
    void init();
    Item add(Item item);
    boolean replace(String id, Item item);
    boolean delete(String id);
    void findAll(Consumer<String> consumer);
    List<Item> findByName(String key);
    Item findById(String id);
}